package simsimbooks.couponserver.domain.category.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.category.entity.Category;

import java.util.Set;

@Getter
public class CategoryResponse {
    private Long id;
    private String name;
    private Long parent;
    private Long[] children;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parent = category.getParent().getId();
        this.children = category.getChildren().stream().map(Category::getId).toArray(Long[]::new);
    }
}
