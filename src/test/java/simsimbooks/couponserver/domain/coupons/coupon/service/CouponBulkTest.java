package simsimbooks.couponserver.domain.coupons.coupon.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTypeConstants;
import simsimbooks.couponserver.domain.user.service.UserService;

@Slf4j
@SpringBootTest
public class CouponBulkTest {
	@Autowired
	private CouponService couponService;

	@Autowired
	private UserService userService;
	@Test
	@DisplayName("생일 쿠폰 벌크 연산 테스트")
	void expireCoupons() {
		long start = System.currentTimeMillis();
		List<Long> userIdsByBirthdayMonth = userService.getUserIdsByBirthdayMonth();
		couponService.bulkIssueCoupons(CouponTypeConstants.BIRTHDAY_COUPON_TYPE_ID, userIdsByBirthdayMonth);
		System.out.println("====================================================================\n");
		System.out.println("실행 방식 : batchUpdate + 다중 VALUES");
		System.out.printf("생일 쿠폰 발급 총 실행시간 (%d건) : {%d}(ms)", userIdsByBirthdayMonth.size(),
			System.currentTimeMillis() - start);
		System.out.println("\n\n====================================================================");
	}
}
