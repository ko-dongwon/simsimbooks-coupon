package simsimbooks.couponserver.domain.coupons.couponpolicy.exception;

public class InvalidCouponPolicyException extends IllegalArgumentException {
    public InvalidCouponPolicyException() {
        super("지원하지 않는 쿠폰 정책 유형입니다.");
    }

    public InvalidCouponPolicyException(String className) {
        super("지원하지 않는 쿠폰 정책 유형입니다. : " + className);
    }
}
