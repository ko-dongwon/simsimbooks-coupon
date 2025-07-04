package simsimbooks.couponserver.domain.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookUpdateRequest {
    private String title;
    private Long salePrice;
    private Long categoryId;
}
