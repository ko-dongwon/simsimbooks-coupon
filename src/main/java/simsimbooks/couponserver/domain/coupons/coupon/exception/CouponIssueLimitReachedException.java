package simsimbooks.couponserver.domain.coupons.coupon.exception;

import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;

public class CouponIssueLimitReachedException extends BusinessException {
  public CouponIssueLimitReachedException() {
    super(ErrorCode.COUPON_ISSUE_LIMIT_REACHED);
  }

  public CouponIssueLimitReachedException(String message) {
    super(ErrorCode.COUPON_ISSUE_LIMIT_REACHED, message);
  }
}
