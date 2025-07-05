package simsimbooks.couponserver.domain.coupons.couponpolicy.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class RateCouponPolicyCreateRequest extends CouponPolicyCreateRequest {
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "1.0")
    private BigDecimal discountRate;

    @NotNull
    @Positive
    private Long maxDiscountAmount;
}
