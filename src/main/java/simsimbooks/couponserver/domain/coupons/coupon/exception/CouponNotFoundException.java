package simsimbooks.couponserver.domain.coupons.coupon.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class CouponNotFoundException extends BusinessException {
    public CouponNotFoundException() {
        super(ErrorCode.COUPON_NOT_FOUND);
    }

    public CouponNotFoundException(String message) {
        super(ErrorCode.COUPON_NOT_FOUND, message);
    }
}
