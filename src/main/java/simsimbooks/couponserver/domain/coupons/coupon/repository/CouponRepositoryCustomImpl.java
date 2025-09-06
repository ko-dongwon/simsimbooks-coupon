package simsimbooks.couponserver.domain.coupons.coupon.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import simsimbooks.couponserver.domain.coupons.coupon.CouponStatus;
import simsimbooks.couponserver.domain.coupons.coupon.dto.CouponSearchCondition;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

import static simsimbooks.couponserver.domain.coupons.coupon.entity.QCoupon.*;
import static simsimbooks.couponserver.domain.coupons.couponpolicy.entity.QCouponPolicy.couponPolicy;
import static simsimbooks.couponserver.domain.coupons.coupontype.entity.QCouponType.couponType;

public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final JdbcTemplate jdbcTemplate;

    public CouponRepositoryCustomImpl(EntityManager em, JdbcTemplate jdbcTemplate) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
		this.jdbcTemplate = jdbcTemplate;
	}

    @Override
    public Page<Coupon> searchByUserId(Long userId, CouponSearchCondition condition, Pageable pageable) {
        List<Coupon> content = queryFactory.selectFrom(coupon)
                .leftJoin(coupon.couponType, couponType).fetchJoin()
                .leftJoin(couponType.couponPolicy, couponPolicy).fetchJoin()
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
    public Boolean existsUnusedCouponByUserId(Long userId, Long couponTypeId) {
        return queryFactory.selectFrom(coupon)
                .where(
                        coupon.user.id.eq(userId),
                        coupon.couponType.id.eq(couponTypeId),
                        statusEq(CouponStatus.UNUSED)
                ).fetchFirst() != null;
    }

    @Override
    public void expireAllOverdueUnusedCoupon() {
        em.flush();
        em.createQuery("update Coupon c set c.status = :expired " +
                        "where c.status = :unused " +
                        "and c.deadline < :now")
                .setParameter("expired", CouponStatus.EXPIRED)
                .setParameter("unused", CouponStatus.UNUSED)
                .setParameter("now", LocalDateTime.now())
                .executeUpdate();

        em.clear();
    }

    @Override
    public void bulkInsert(List<Coupon> coupons) {
        em.flush();
        String sql = "INSERT INTO coupons (issued_at, used_at, deadline, status, coupon_type_id, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Coupon coupon = coupons.get(i);
                ps.setTimestamp(1, Timestamp.valueOf(coupon.getIssuedAt()));
                ps.setNull(2, Types.TIMESTAMP);
                ps.setTimestamp(3,Timestamp.valueOf(coupon.getDeadline()));
                ps.setString(4, coupon.getCouponType().getName());
                ps.setLong(5, coupon.getCouponType().getId());
                ps.setLong(6, coupon.getUser().getId());
            }

            @Override
            public int getBatchSize() {
                return coupons.size();
            }
        });

        em.clear();
    }

    private BooleanExpression statusEq(CouponStatus status) {
        return status == null ? null : coupon.status.eq(status);
    }

    private BooleanExpression targetTypeEq(CouponTargetType targetType) {
        return targetType == null ? null : couponType.targetType.eq(targetType);
    }
}
