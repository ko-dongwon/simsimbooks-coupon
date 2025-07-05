package simsimbooks.couponserver.domain.coupons.couponpolicy.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.RateCouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;

import java.math.BigDecimal;

@Getter
public class RateCouponPolicyResponse extends CouponPolicyResponse{
    private BigDecimal discountRate;
    private Long maxDiscountAmount;

    public RateCouponPolicyResponse(RateCouponPolicy couponPolicy) {
        super(couponPolicy.getId(), couponPolicy.getName(), couponPolicy.getMinOrderAmount(), DiscountType.RATE);
        this.discountRate = couponPolicy.getDiscountRate();
        this.maxDiscountAmount = couponPolicy.getMaxDiscountAmount();
    }
}
