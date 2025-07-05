package simsimbooks.couponserver.domain.coupons.couponpolicy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.CouponPolicy;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy,Long> {
}
