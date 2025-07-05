package simsimbooks.couponserver.domain.coupons.coupon.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class CouponAlreadyUsedException extends BusinessException {
    public CouponAlreadyUsedException() {
        super(ErrorCode.COUPON_ALREADY_USED);
    }

    public CouponAlreadyUsedException(String message) {
        super(ErrorCode.COUPON_ALREADY_USED, message);
    }
}
