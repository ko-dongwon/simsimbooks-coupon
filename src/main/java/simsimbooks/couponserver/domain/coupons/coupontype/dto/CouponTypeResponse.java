package simsimbooks.couponserver.domain.coupons.coupontype.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.coupons.coupontype.entity.CouponType;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;

@Getter
public class CouponTypeResponse {
    private Long id;
    private String name;
    private Integer period;
    private CouponTargetType targetType;
    private Long targetId;
    private Long couponPolicyId;

    public CouponTypeResponse(CouponType couponType) {
        this.id = couponType.getId();
        this.name = couponType.getName();
        this.period = couponType.getPeriod();
        this.targetType = couponType.getTargetType();
        this.targetId = couponType.getTargetId();
        this.couponPolicyId = couponType.getCouponPolicy().getId();
    }
}
