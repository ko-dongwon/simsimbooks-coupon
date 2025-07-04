package simsimbooks.couponserver.domain.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequest {
    private String name;
    private Long parentId;
}
