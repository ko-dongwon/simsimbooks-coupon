package simsimbooks.couponserver.domain.book.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simsimbooks.couponserver.common.entity.BaseTimeEntity;
import simsimbooks.couponserver.domain.category.entity.Category;

import java.util.Optional;

@Entity
@Table(name = "books")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    private Long salePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Book(String title, Long salePrice, Category category) {
        this.title = title;
        this.salePrice = salePrice;
        this.category = category;
    }

    // 정적 팩토리 메서드
    public static Book of(String title, Long salePrice, Category category) {
        return new Book(title, salePrice, category);
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void updateBookInfo(@Nullable String title, @Nullable Long salePrice) {
        Optional.ofNullable(title).ifPresent(t -> this.title = t);
        Optional.ofNullable(salePrice).ifPresent(p -> this.salePrice = p);
    }
}
