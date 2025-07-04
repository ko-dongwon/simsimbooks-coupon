package simsimbooks.couponserver.domain.coupons.couponpolicy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;

import java.math.BigDecimal;
import java.util.Optional;

@Entity
@DiscriminatorValue(value = "RATE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RateCouponPolicy extends CouponPolicy{

    @Column(name = "discount_rate")
    private BigDecimal discountRate;

    @Column(name = "max_discount_amount")
    private Long maxDiscountAmount;

    protected RateCouponPolicy(String name, BigDecimal discountRate, Long maxDiscountAmount, Long minOrderAmount) {
        super(name, DiscountType.RATE, minOrderAmount);
        this.discountRate = discountRate;
        this.maxDiscountAmount = maxDiscountAmount;
    }

}
