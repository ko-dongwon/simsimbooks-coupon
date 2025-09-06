package simsimbooks.couponserver.domain.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookCreateRequest {
    @NotBlank
    private String title;
    @NotNull
    private Long salePrice;
    @NotNull
    private Long categoryId;
}
