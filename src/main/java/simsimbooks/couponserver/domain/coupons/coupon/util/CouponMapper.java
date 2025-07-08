package simsimbooks.couponserver.domain.coupons.coupon.util;

import simsimbooks.couponserver.domain.coupons.coupon.dto.CouponResponse;
import simsimbooks.couponserver.domain.coupons.coupon.dto.FixCouponResponse;
import simsimbooks.couponserver.domain.coupons.coupon.dto.RateCouponResponse;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.CouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;
import simsimbooks.couponserver.domain.coupons.couponpolicy.exception.InvalidCouponPolicyException;

public class CouponMapper {
    public static CouponResponse toResponse(Coupon coupon) {
        DiscountType discountType = coupon.getCouponType().getCouponPolicy().getDiscountType();
        return switch (discountType) {
            case FIX ->  new FixCouponResponse(coupon);
            case RATE -> new RateCouponResponse(coupon);
            default -> throw new InvalidCouponPolicyException();
        };
    }
}
