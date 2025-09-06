package simsimbooks.couponserver.domain.coupons.couponpolicy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FixCouponPolicyCreateRequest extends CouponPolicyCreateRequest{
    @NotNull
    @Positive
    private Long discountPrice;
}
