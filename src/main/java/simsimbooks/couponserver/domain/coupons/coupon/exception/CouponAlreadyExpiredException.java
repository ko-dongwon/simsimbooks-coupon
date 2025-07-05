package simsimbooks.couponserver.domain.coupons.coupon.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class CouponAlreadyExpiredException extends BusinessException {

    public CouponAlreadyExpiredException() {
        super(ErrorCode.COUPON_ALREADY_EXPIRED);
    }

    public CouponAlreadyExpiredException(String message) {
        super(ErrorCode.COUPON_ALREADY_EXPIRED, message);
    }
}
