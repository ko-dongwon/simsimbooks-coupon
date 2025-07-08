package simsimbooks.couponserver.domain.coupons.coupontype.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simsimbooks.couponserver.common.entity.BaseTimeEntity;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.CouponPolicy;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "coupon_types")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponType extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_type_id")
    private Long id;

    private String name;

    private Integer period;

    private LocalDateTime deadline;

    private Integer remainIssueCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id")
    private CouponPolicy couponPolicy;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "target_type")
    private CouponTargetType targetType;

    @Column(name = "target_id")
    private Long targetId;

    private CouponType(String name, Integer period, LocalDateTime deadline, CouponPolicy couponPolicy, Integer remainIssueCnt, CouponTargetType targetType, Long targetId) {
        this.name = name;
        this.period = period;
        this.deadline = deadline;
        this.couponPolicy = couponPolicy;
        this.targetType = targetType;
        this.targetId = targetId;
        this.remainIssueCnt = remainIssueCnt;
    }

    public static CouponType of(String name, Integer period,@Nullable LocalDateTime deadline, CouponPolicy couponPolicy,Integer remainIssueCnt, CouponTargetType targetType, Long targetId) {
        if(deadline == null) deadline = LocalDateTime.of(2999, 1, 1, 0, 0);
        if(targetType == CouponTargetType.ALL) targetId = 0L;
        return new CouponType(name, period, deadline, couponPolicy, remainIssueCnt, targetType, targetId);
    }

    public boolean canIssue() {
        return remainIssueCnt > 0;
    }

    public void issue() {
        remainIssueCnt--;
    }

    public void updateName(@Nullable String name) {
        Optional.ofNullable(name).ifPresent(n -> this.name = n);
    }

    public void updatePeriod(@Nullable Integer period) {
        Optional.ofNullable(period).ifPresent(p -> this.period = p);
    }

    public void updateDeadline(@Nullable LocalDateTime deadline) {
        Optional.ofNullable(deadline).ifPresent(d -> this.deadline = d);
    }

    public void updateRemainIssueCnt(@Nullable Integer remainIssueCnt) {
        Optional.ofNullable(remainIssueCnt).ifPresent(c -> this.remainIssueCnt = c);
    }
    
    
}
