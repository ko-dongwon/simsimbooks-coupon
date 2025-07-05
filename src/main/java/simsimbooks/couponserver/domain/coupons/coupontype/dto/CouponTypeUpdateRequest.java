package simsimbooks.couponserver.domain.coupons.coupontype.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CouponTypeUpdateRequest {
    private String name;
    private Integer period;
    private LocalDateTime deadline;
}
