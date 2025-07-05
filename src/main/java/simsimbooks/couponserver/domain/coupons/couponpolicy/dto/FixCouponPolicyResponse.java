package simsimbooks.couponserver.domain.coupons.couponpolicy.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.FixCouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;

@Getter
public class FixCouponPolicyResponse extends CouponPolicyResponse{
    private Long discountPrice;

    public FixCouponPolicyResponse(FixCouponPolicy couponPolicy) {
        super(couponPolicy.getId(), couponPolicy.getName(), couponPolicy.getMinOrderAmount(), DiscountType.FIX);
        this.discountPrice = couponPolicy.getDiscountPrice();
    }
}
