package simsimbooks.couponserver.domain.coupons.couponpolicy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;

import java.util.Optional;


@Entity
@DiscriminatorValue(value = "FIX")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FixCouponPolicy extends CouponPolicy {
    @Column(name = "discount_price")
    private Long discountPrice;

    protected FixCouponPolicy(String name, Long discountPrice, Long minOrderAmount) {
        super(name, DiscountType.FIX, minOrderAmount);
        this.discountPrice = discountPrice;
    }

}