package simsimbooks.couponserver.domain.coupons.coupontype.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.coupons.coupontype.entity.CouponType;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;

import java.time.LocalDateTime;

@Getter
public class CouponTypeResponse {
    private Long id;
    private String name;
    private Integer period;
    private LocalDateTime deadline;
    private CouponTargetType targetType;
    private Long targetId;
    private Long couponPolicyId;
    private Integer maxIssueCnt;

    public CouponTypeResponse(CouponType couponType) {
        this.id = couponType.getId();
        this.name = couponType.getName();
        this.period = couponType.getPeriod();
        this.deadline = couponType.getDeadline();
        this.maxIssueCnt = couponType.getMaxIssueCnt();
        this.targetType = couponType.getTargetType();
        this.targetId = couponType.getTargetId();
        this.couponPolicyId = couponType.getCouponPolicy().getId();
    }
}
