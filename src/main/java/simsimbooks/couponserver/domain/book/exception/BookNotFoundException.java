package simsimbooks.couponserver.domain.book.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class BookNotFoundException extends BusinessException {
    public BookNotFoundException() {
        super(ErrorCode.BOOK_NOT_FOUND);
    }

    public BookNotFoundException( String message) {
        super(ErrorCode.BOOK_NOT_FOUND);
    }
}
