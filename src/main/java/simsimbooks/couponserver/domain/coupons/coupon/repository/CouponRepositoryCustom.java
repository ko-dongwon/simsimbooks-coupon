package simsimbooks.couponserver.domain.coupons.coupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import simsimbooks.couponserver.domain.coupons.coupon.dto.CouponSearchCondition;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;

public interface CouponRepositoryCustom {
    Page<Coupon> searchByUserId(Long userId, CouponSearchCondition condition, Pageable pageable);

    Boolean existsUnusedCouponByUserId(Long userId);

    void expireAllOverdueUnusedCoupon();

}
