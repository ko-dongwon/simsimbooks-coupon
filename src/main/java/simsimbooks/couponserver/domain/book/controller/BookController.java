package simsimbooks.couponserver.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsimbooks.couponserver.common.dto.ApiResponse;
import simsimbooks.couponserver.domain.book.dto.BookCreateRequest;
import simsimbooks.couponserver.domain.book.dto.BookResponse;
import simsimbooks.couponserver.domain.book.dto.BookUpdateRequest;
import simsimbooks.couponserver.domain.book.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping // Create
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@RequestBody BookCreateRequest requestDto) {
        BookResponse response = bookService.createBook(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response, "책이 생성되었습니다."));
    }

    @GetMapping("/{bookId}") // Read
    public ResponseEntity<ApiResponse<BookResponse>> getBook(@PathVariable Long bookId) {
        BookResponse response = bookService.getBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response, "책 정보를 조회했습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookResponse>>> getBooks(@PageableDefault(sort = "title", direction = Sort.Direction.ASC)
                                                                        Pageable pageable) {
        Page<BookResponse> page = bookService.getBooks(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(page, "책 정보 페이지를 조회했습니다"));
    }

    @PatchMapping("/{bookId}") //Update
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable Long bookId,
                                                      @RequestBody BookUpdateRequest requestDto) {
        BookResponse response = bookService.updateBook(bookId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response,"책 정보를 업데이트했습니다."));
    }

    @DeleteMapping("/{bookId}") //Delete
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null,"책 정보를 삭제했습니다."));
    }
}
