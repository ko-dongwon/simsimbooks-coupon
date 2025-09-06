package simsimbooks.couponserver.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"회원을 찾을 수 없습니다."),

    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND,"책을 찾을 수 없습니다."),

    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다."),

    COUPON_ISSUE_LIMIT_REACHED(HttpStatus.CONFLICT,"쿠폰을 더 이상 발급할 수 없습니다."),
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "쿠폰을 찾을 수 없습니다."),
    COUPON_ALREADY_EXPIRED(HttpStatus.BAD_REQUEST, "쿠폰이 이미 만료되었습니다."),
    COUPON_ALREADY_USED(HttpStatus.BAD_REQUEST, "쿠폰이 이미 사용되었습니다."),
    COUPON_CANNOT_APPLY(HttpStatus.BAD_REQUEST, "쿠폰을 적용할 수 없습니다."),
    COUPON_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND,"쿠폰 타입을 찾을 수 없습니다."),
    COUPON_TYPE_DELETE_FAIL(HttpStatus.CONFLICT, "쿠폰 타입을 삭제할 수 없습니다."),
    USER_ALREADY_HAS_COUPON(HttpStatus.CONFLICT, "회원은 이미 쿠폰을 가지고 가지고 있습니다."),
    COUPON_POLICY_DELETE_FAIL(HttpStatus.CONFLICT, "쿠폰 정책을 삭제할 수 없습니다."),
    COUPON_POLICY_NOT_FOUND(HttpStatus.NOT_FOUND, "쿠폰 정책을 찾을 수 없습니다."),

    SERVICE_BUSY(HttpStatus.SERVICE_UNAVAILABLE, "서버가 과부하 상태입니다. 잠시 후 다시 시도해 주세요.");

    private final HttpStatus status;
    private final String message;
}
