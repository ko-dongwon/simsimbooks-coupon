package simsimbooks.couponserver.domain.coupons.coupontype.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CouponTypeCreateRequest {
    @NotBlank
    @Size(min = 1,max = 40)
    private String name;
    @NotNull
    private CouponTargetType targetType;
    @NotNull
    private Long targetId;
    @NotNull
    private Integer period;
    private LocalDateTime deadline;
    @NotNull
    private Long couponPolicyId;
    @NotNull
    @Positive
    private Integer maxIssueCnt;
}
