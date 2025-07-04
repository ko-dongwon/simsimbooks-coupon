package simsimbooks.couponserver.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryCreateRequest {
    @NotBlank
    private String name;
    private Long parentId;
}
