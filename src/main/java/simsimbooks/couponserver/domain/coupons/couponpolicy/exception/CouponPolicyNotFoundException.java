package simsimbooks.couponserver.domain.coupons.couponpolicy.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class CouponPolicyNotFoundException extends BusinessException {
    public CouponPolicyNotFoundException() {
        super(ErrorCode.COUPON_POLICY_NOT_FOUND);
    }

    public CouponPolicyNotFoundException(String message) {
        super(ErrorCode.COUPON_POLICY_NOT_FOUND, message);
    }
}
