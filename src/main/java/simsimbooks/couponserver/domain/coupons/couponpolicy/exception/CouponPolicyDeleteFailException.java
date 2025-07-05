package simsimbooks.couponserver.domain.coupons.couponpolicy.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class CouponPolicyDeleteFailException extends BusinessException {
    public CouponPolicyDeleteFailException() {
        super(ErrorCode.COUPON_POLICY_DELETE_FAIL);
    }

    public CouponPolicyDeleteFailException(String message) {
        super(ErrorCode.COUPON_POLICY_DELETE_FAIL, message);
    }
}
