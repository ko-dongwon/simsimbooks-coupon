package simsimbooks.couponserver.domain.coupons.coupon.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import simsimbooks.couponserver.domain.coupons.coupon.CouponStatus;
import simsimbooks.couponserver.domain.coupons.coupon.dto.CouponSearchCondition;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;

import java.time.LocalDateTime;
import java.util.List;

import static simsimbooks.couponserver.domain.coupons.coupon.entity.QCoupon.*;
import static simsimbooks.couponserver.domain.coupons.couponpolicy.entity.QCouponPolicy.couponPolicy;
import static simsimbooks.couponserver.domain.coupons.coupontype.entity.QCouponType.couponType;

public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CouponRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Coupon> searchByUserId(Long userId, CouponSearchCondition condition, Pageable pageable) {
        List<Coupon> content = queryFactory.selectFrom(coupon)
                .leftJoin(coupon.couponType, couponType)
                .leftJoin(couponType.couponPolicy, couponPolicy)
                .where(
                        coupon.user.id.eq(userId),
                        statusEq(condition.getStatus()),
                        targetTypeEq(condition.getTargetType())
                )
                .orderBy(coupon.deadline.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(coupon.count())
                .from(coupon)
                .leftJoin(coupon.couponType, couponType)
                .where(
                        coupon.user.id.eq(userId),
                        statusEq(condition.getStatus()),
                        targetTypeEq(condition.getTargetType())
                )
                .orderBy(coupon.deadline.desc());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Boolean existsUnusedCouponByUserId(Long userId) {
        return queryFactory.selectFrom(coupon)
                .where(
                        coupon.user.id.eq(userId),
                        statusEq(CouponStatus.UNUSED)
                ).fetchOne() != null;
    }

    @Override
    public void expireAllOverdueUnusedCoupon() {
        em.createQuery("update Coupon c set c.status = :expired " +
                        "where c.status = :unused " +
                        "and c.deadline < :now")
                .setParameter("expired", CouponStatus.EXPIRED)
                .setParameter("unused", CouponStatus.UNUSED)
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();

        em.flush();
        em.clear();
    }


    private BooleanExpression statusEq(CouponStatus status) {
        return status == null ? null : coupon.status.eq(status);
    }

    private BooleanExpression targetTypeEq(CouponTargetType targetType) {
        return targetType == null ? null : couponType.targetType.eq(targetType);
    }
}
