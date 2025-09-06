package simsimbooks.couponserver.domain.coupons.coupon.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class CouponCannotApplyException extends BusinessException {
    public CouponCannotApplyException() {
        super(ErrorCode.COUPON_CANNOT_APPLY);
    }

    public CouponCannotApplyException(String message) {
        super(ErrorCode.COUPON_CANNOT_APPLY, message);
    }
}
