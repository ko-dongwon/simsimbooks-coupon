package simsimbooks.couponserver.domain.coupons.coupon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import simsimbooks.couponserver.domain.coupons.coupon.dto.CouponSearchCondition;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;

public interface CouponRepositoryCustom {
    Page<Coupon> searchByUserId(Long userId, CouponSearchCondition condition, Pageable pageable);

    Boolean existsUnusedCouponByUserId(Long userId, Long couponType);

    void expireAllOverdueUnusedCoupon();

    void bulkInsert(List<Coupon> coupons);
}
