package simsimbooks.couponserver.domain.coupons.coupon.dto;

import lombok.Getter;
import org.hibernate.Hibernate;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.FixCouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;
import simsimbooks.couponserver.domain.coupons.couponpolicy.exception.InvalidCouponPolicyException;

@Getter
public class FixCouponResponse extends CouponResponse{
    private final DiscountType discountType = DiscountType.FIX;
    private Long discountPrice;

    public FixCouponResponse(Coupon coupon) {
        super(coupon, coupon.getCouponType(), coupon.getCouponType().getCouponPolicy());
        if (Hibernate.unproxy(coupon.getCouponType().getCouponPolicy()) instanceof FixCouponPolicy fixPolicy) {
            this.discountPrice = fixPolicy.getDiscountPrice();
        } else throw new InvalidCouponPolicyException();

    }
}
