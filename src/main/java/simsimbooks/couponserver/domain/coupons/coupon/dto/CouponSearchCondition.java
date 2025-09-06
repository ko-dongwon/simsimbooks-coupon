package simsimbooks.couponserver.domain.coupons.coupon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simsimbooks.couponserver.domain.coupons.coupon.CouponStatus;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;

@Getter
@NoArgsConstructor
public class CouponSearchCondition {
    private CouponStatus status;
    private CouponTargetType targetType;
}
