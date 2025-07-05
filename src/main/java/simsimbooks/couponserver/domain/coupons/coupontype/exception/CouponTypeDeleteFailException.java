package simsimbooks.couponserver.domain.coupons.coupontype.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class CouponTypeDeleteFailException extends BusinessException {
    public CouponTypeDeleteFailException() {
        super(ErrorCode.COUPON_TYPE_DELETE_FAIL);
    }

    public CouponTypeDeleteFailException(String message) {
        super(ErrorCode.COUPON_TYPE_DELETE_FAIL, message);
    }
}
