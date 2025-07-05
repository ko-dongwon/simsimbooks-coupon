package simsimbooks.couponserver.domain.coupons.coupon.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simsimbooks.couponserver.domain.coupons.coupon.CouponStatus;
import simsimbooks.couponserver.domain.coupons.coupontype.entity.CouponType;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;
import simsimbooks.couponserver.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "coupons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Enumerated(value = EnumType.STRING)
    private CouponStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private CouponType couponType;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Coupon(User user, CouponType couponType,LocalDateTime deadline) {
        this.issuedAt = LocalDateTime.now();
        this.deadline = deadline;
        this.status = CouponStatus.UNUSED;
        this.couponType = couponType;
        this.user = user;
    }

    public static Coupon of(User user, CouponType couponType) {
        // coupontype의 Deadline과 오늘부터 coupontype의 period를 더한 것 중 더 먼저인 날짜를 쿠폰 유효기간으로 설정
        LocalDateTime couponDeadline = couponType.getDeadline();
        LocalDateTime periodDeadline = LocalDateTime.now().plusDays(couponType.getPeriod());
        LocalDateTime deadline = couponDeadline.isBefore(periodDeadline) ? couponDeadline : periodDeadline;

        return new Coupon(user, couponType, deadline);
    }

    public void use() {
        this.status = CouponStatus.USED;
        this.usedAt = LocalDateTime.now();
    }

    public void expire() {
        this.status = CouponStatus.EXPIRED;
    }

    public boolean isApplicable(CouponTargetType targetType, Long targetId) {
        return targetType == couponType.getTargetType() || Objects.equals(couponType.getTargetId(), targetId);
    }
}
