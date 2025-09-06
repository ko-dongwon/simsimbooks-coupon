package simsimbooks.couponserver.domain.book.dto;

import lombok.Getter;
import simsimbooks.couponserver.domain.book.entity.Book;

@Getter
public class BookResponse {
    private Long id;
    private String title;
    private Long salePrice;
    private Long categoryId;
    private String categoryName;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.salePrice = book.getSalePrice();
        this.categoryId = book.getCategory().getId();
        this.categoryName = book.getCategory().getName();
    }
}
