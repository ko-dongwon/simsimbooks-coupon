package simsimbooks.couponserver.common.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import simsimbooks.couponserver.domain.coupons.coupon.service.CouponService;

@Component
@RequiredArgsConstructor
public class CouponScheduler {
    private final CouponService couponService;

    /*
     * fixedRate -> 작업이 시작하는 시점을 기준으로 특정 시간 이후 작업을 반복
     * fixedDelay -> 작업이 끝나는 시점을 기준으로 특정 시간 이휴 작업을 반복
     * cron -> 표현식을 사용해 작업 반복
     * */

    /**
     * 매일 00시 10분에 deadline이 지났는데 status가 아직 UNUSED인 쿠폰을 모두 만료처리한다.
     */
    @Scheduled(cron = "0 10 0 * * *")
    public void expireCoupons() {
        couponService.expireOverdueUnusedCoupon();
    }
}
