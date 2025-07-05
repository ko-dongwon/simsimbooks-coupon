package simsimbooks.couponserver.domain.coupons.couponpolicy.util;

import simsimbooks.couponserver.domain.coupons.couponpolicy.dto.*;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.CouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.FixCouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.RateCouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.exception.InvalidCouponPolicyException;

public class CouponPolicyMapper {
    public static CouponPolicyResponse toResponse(CouponPolicy couponPolicy) {
        if(couponPolicy instanceof FixCouponPolicy fix) return new FixCouponPolicyResponse(fix);
        if(couponPolicy instanceof RateCouponPolicy rate) return new RateCouponPolicyResponse(rate);

        throw new InvalidCouponPolicyException(couponPolicy.getClass().getName());
    }

    public static CouponPolicy toEntity(CouponPolicyCreateRequest dto) {
        if(dto instanceof FixCouponPolicyCreateRequest fix) return CouponPolicy.ofFix(fix.getName(), fix.getDiscountPrice(), fix.getMinOrderAmount());
        if(dto instanceof RateCouponPolicyCreateRequest rate) return CouponPolicy.ofRate(rate.getName(), rate.getDiscountRate(), rate.getMaxDiscountAmount(), rate.getMinOrderAmount());
        throw new InvalidCouponPolicyException(dto.getClass().getName());
    }
}
