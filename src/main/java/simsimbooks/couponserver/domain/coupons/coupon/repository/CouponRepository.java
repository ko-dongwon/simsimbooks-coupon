package simsimbooks.couponserver.domain.coupons.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;
import simsimbooks.couponserver.domain.coupons.coupontype.entity.CouponType;

public interface CouponRepository extends JpaRepository<Coupon,Long>, CouponRepositoryCustom {
    long countByCouponType(CouponType couponType);
}
