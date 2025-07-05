package simsimbooks.couponserver.domain.coupons.couponpolicy.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simsimbooks.couponserver.domain.coupons.couponpolicy.enums.DiscountType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "discountType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixCouponPolicyCreateRequest.class, name = "FIX"),
        @JsonSubTypes.Type(value = RateCouponPolicyCreateRequest.class, name = "RATE")
})
@Getter
@NoArgsConstructor
public abstract class CouponPolicyCreateRequest {
    @NotBlank
    private String name;

    @NotNull
    @PositiveOrZero
    private Long minOrderAmount;

    @NotNull
    private DiscountType discountType;
}
