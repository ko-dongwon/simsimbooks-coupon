package simsimbooks.couponserver.domain.coupons.coupon.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import simsimbooks.couponserver.config.IntegrationTest;
import simsimbooks.couponserver.domain.coupons.coupon.repository.CouponRepository;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.CouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.repository.CouponPolicyRepository;
import simsimbooks.couponserver.domain.coupons.coupontype.entity.CouponType;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;
import simsimbooks.couponserver.domain.coupons.coupontype.repository.CouponTypeRepository;
import simsimbooks.couponserver.domain.user.entity.User;
import simsimbooks.couponserver.domain.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@IntegrationTest
class CouponServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponPolicyRepository couponPolicyRepository;

    @Autowired
    private CouponTypeRepository couponTypeRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponService couponService;

    private CouponType testCouponType;

    private List<User> testUsers;

    @BeforeEach
    void setUp() {
        CouponPolicy couponPolicy = couponPolicyRepository.save(CouponPolicy.ofFix("정액 쿠폰 할인 정책", 10000L, 20000L));
        int maxIssueCnt = 30;
        // 테스트용 쿠폰 타입
        testCouponType = couponTypeRepository.save(CouponType.of("신년 맞이 선착순 쿠폰", 90, null, couponPolicy, maxIssueCnt, CouponTargetType.ALL, 0L));

        // 테스트용 유저
        int testUsersCnt = 200;
        testUsers = userRepository.saveAll(IntStream.range(0, testUsersCnt)
                .mapToObj(i -> User.of("test" + i, UUID.randomUUID().toString() + "@test.com", LocalDate.of(2000, 1, 1)))
                .toList());
    }

    @Test
    @DisplayName("쿠폰 발급 - 동시성 테스트")
    void issueCoupon() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(testUsers.size());

        for (int i = 0; i < testUsers.size(); i++) {
            final int index = i;
            executorService.submit(() -> {
                try {
                    couponService.issueCoupon(testCouponType.getId(), testUsers.get(index).getId());
                } catch (Exception e) {
                    log.error("쿠폰 발급 실패", e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        //메인스레드 여기서 쓰레드들을 기다림 (최대 10초)
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();

        CouponType updatedCouponType = couponTypeRepository.findById(testCouponType.getId()).orElseThrow();
        long actualCouponCount = couponRepository.countByCouponType(updatedCouponType);

        log.info("===== 테스트 결과 =====");
        log.info("쿠폰 최대 발급 가능 횟수 : {}", testCouponType.getMaxIssueCnt());
        log.info("쿠폰 현재 발급 수 : {}", updatedCouponType.getCurrentIssueCnt());
        log.info("실제 생성된 쿠폰 레코드 수 : {}", actualCouponCount);

        // 검증 1 : 실제 생성된 쿠폰 수와 쿠폰 타입의 발급 수가 다름.
        assertThat(updatedCouponType.getCurrentIssueCnt()).isEqualTo(actualCouponCount);
        // 검증 2 : 발급 수는 최대 발급 수를 초과하면 안됨.
        assertThat(updatedCouponType.getCurrentIssueCnt()).isEqualTo(updatedCouponType.getMaxIssueCnt());
    }
}
