package simsimbooks.couponserver.domain.coupons.coupon.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.coupons.coupon.CouponStatus;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.CouponPolicy;
import simsimbooks.couponserver.domain.coupons.coupontype.entity.CouponType;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;

import java.time.LocalDateTime;

@Getter
public abstract class CouponResponse {
    private Long id;
    private LocalDateTime issuedAt;
    private LocalDateTime deadline;
    private CouponStatus status;
    // couponType의 이름
    private String name;
    private CouponTargetType targetType;
    private Long targetId;
    private Long minOrderAmount;

    protected CouponResponse(Coupon coupon, CouponType couponType, CouponPolicy couponPolicy) {
        this.id = coupon.getId();
        this.issuedAt = coupon.getIssuedAt();
        this.deadline = coupon.getDeadline();
        this.status = coupon.getStatus();
        this.name = couponType.getName();
        this.targetType = couponType.getTargetType();
        this.targetId = couponType.getTargetId();
        this.minOrderAmount = couponPolicy.getMinOrderAmount();
    }
}
