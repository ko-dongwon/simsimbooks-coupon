package simsimbooks.couponserver.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simsimbooks.couponserver.common.util.DtoMapper;
import simsimbooks.couponserver.domain.book.dto.BookCreateRequest;
import simsimbooks.couponserver.domain.book.dto.BookResponse;
import simsimbooks.couponserver.domain.book.dto.BookUpdateRequest;
import simsimbooks.couponserver.domain.book.entity.Book;
import simsimbooks.couponserver.domain.book.exception.BookNotFoundException;
import simsimbooks.couponserver.domain.book.repository.BookRepository;
import simsimbooks.couponserver.domain.category.entity.Category;
import simsimbooks.couponserver.domain.category.exception.CategoryNotFoundException;
import simsimbooks.couponserver.domain.category.repository.CategoryRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public BookResponse createBook(BookCreateRequest requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        Book save = bookRepository.save(Book.of(requestDto.getTitle(), requestDto.getSalePrice(), category));
        return DtoMapper.toDto(save, BookResponse.class);
    }

    public BookResponse getBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        return DtoMapper.toDto(book, BookResponse.class);
    }

    public Page<BookResponse> getBooks(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return page.map(e -> DtoMapper.toDto(e, BookResponse.class));
    }

    @Transactional
    public BookResponse updateBook(Long bookId, BookUpdateRequest requestDto) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        // 카테고리 변경
        if (Objects.nonNull(requestDto.getCategoryId())) {
            Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
            book.changeCategory(category);
        }

        book.updateBookInfo(requestDto.getTitle(), requestDto.getSalePrice());
        return DtoMapper.toDto(book, BookResponse.class);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }
}
