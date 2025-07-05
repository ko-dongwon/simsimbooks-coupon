package simsimbooks.couponserver.domain.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simsimbooks.couponserver.domain.book.entity.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
