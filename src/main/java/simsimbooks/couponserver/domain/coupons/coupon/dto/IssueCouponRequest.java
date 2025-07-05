package simsimbooks.couponserver.domain.coupons.coupon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueCouponRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long couponTypeId;
}
