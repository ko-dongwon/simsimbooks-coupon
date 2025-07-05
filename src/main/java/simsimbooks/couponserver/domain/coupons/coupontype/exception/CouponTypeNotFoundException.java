package simsimbooks.couponserver.domain.coupons.coupontype.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class CouponTypeNotFoundException extends BusinessException {
    public CouponTypeNotFoundException() {
        super(ErrorCode.COUPON_TYPE_NOT_FOUND);
    }

    public CouponTypeNotFoundException(String message) {
        super(ErrorCode.COUPON_TYPE_NOT_FOUND, message);
    }
}
