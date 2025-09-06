package simsimbooks.couponserver.domain.coupons.couponpolicy.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;

@Getter
public abstract class CouponPolicyResponse {
    private Long id;
    private String name;
    private Long minOrderAmount;
    private DiscountType discountType;

    protected CouponPolicyResponse(Long id, String name, Long minOrderAmount, DiscountType discountType) {
        this.id = id;
        this.name = name;
        this.minOrderAmount = minOrderAmount;
        this.discountType = discountType;
    }
}
