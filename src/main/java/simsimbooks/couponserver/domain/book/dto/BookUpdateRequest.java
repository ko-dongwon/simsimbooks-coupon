package simsimbooks.couponserver.domain.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookUpdateRequest {
    private String title;
    private Long salePrice;
    private Long categoryId;
}
