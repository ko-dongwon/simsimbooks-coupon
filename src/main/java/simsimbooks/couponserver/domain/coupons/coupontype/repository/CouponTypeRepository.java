package simsimbooks.couponserver.domain.coupons.coupontype.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.CouponPolicy;
import simsimbooks.couponserver.domain.coupons.coupontype.entity.CouponType;

import java.util.Optional;

public interface CouponTypeRepository extends JpaRepository<CouponType,Long> {
    long countByCouponPolicy(CouponPolicy couponPolicy);

    Page<CouponType> findAllByCouponPolicy(CouponPolicy couponPolicy, Pageable pageable);
}
