package simsimbooks.couponserver.domain.coupons.couponpolicy.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simsimbooks.couponserver.common.entity.BaseTimeEntity;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;

import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Table(name = "coupon_policies")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class CouponPolicy extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_policy_id")
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "min_order_amount")
    private Long minOrderAmount;

    protected CouponPolicy(String name, DiscountType discountType, Long minOrderAmount) {
        this.name = name;
        this.discountType = discountType;
        this.minOrderAmount = minOrderAmount;
    }

    public static RateCouponPolicy ofRate(String name, BigDecimal discountRate, Long maxDiscountAmount, Long minOrderAmount) {
        return new RateCouponPolicy(name, discountRate, maxDiscountAmount, minOrderAmount);
    }

    public static FixCouponPolicy ofFix(String name, Long discountPrice, Long minOrderAmount) {
        return new FixCouponPolicy(name, discountPrice, minOrderAmount);
    }

    public void updateName(@Nullable String name) {
        Optional.ofNullable(name).ifPresent(n -> this.name = n);
    }
}
