package simsimbooks.couponserver.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다"),

    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
