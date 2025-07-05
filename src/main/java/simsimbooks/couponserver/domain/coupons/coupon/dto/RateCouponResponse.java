package simsimbooks.couponserver.domain.coupons.coupon.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.RateCouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;
import simsimbooks.couponserver.domain.coupons.couponpolicy.exception.InvalidCouponPolicyException;

import java.math.BigDecimal;

@Getter
public class RateCouponResponse extends CouponResponse {
    private final DiscountType discountType = DiscountType.RATE;
    private BigDecimal discountRate;
    private Long maxDiscountAmount;

    public RateCouponResponse(Coupon coupon) {
        super(coupon, coupon.getCouponType(), coupon.getCouponType().getCouponPolicy());
        if (coupon.getCouponType().getCouponPolicy() instanceof RateCouponPolicy ratePolicy) {
            this.discountRate = ratePolicy.getDiscountRate();
            this.maxDiscountAmount = ratePolicy.getMaxDiscountAmount();
        }
        throw new InvalidCouponPolicyException();

    }
}
