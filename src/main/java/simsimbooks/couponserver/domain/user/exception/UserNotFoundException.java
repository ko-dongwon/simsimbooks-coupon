package simsimbooks.couponserver.domain.user.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException( String message) {
        super(ErrorCode.USER_NOT_FOUND, message);
    }
}
