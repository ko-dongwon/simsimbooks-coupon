package simsimbooks.couponserver.domain.coupons.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;
import simsimbooks.couponserver.common.lock.DistributedLock;
import simsimbooks.couponserver.common.lock.LockTarget;
import simsimbooks.couponserver.domain.coupons.coupon.CouponStatus;
import simsimbooks.couponserver.domain.coupons.coupon.dto.CouponResponse;
import simsimbooks.couponserver.domain.coupons.coupon.dto.CouponSearchCondition;
import simsimbooks.couponserver.domain.coupons.coupon.dto.IssueCouponRequest;
import simsimbooks.couponserver.domain.coupons.coupon.entity.Coupon;
import simsimbooks.couponserver.domain.coupons.coupon.exception.*;
import simsimbooks.couponserver.domain.coupons.coupon.repository.CouponRepository;
import simsimbooks.couponserver.domain.coupons.coupon.util.CouponMapper;
import simsimbooks.couponserver.domain.coupons.coupontype.entity.CouponType;
import simsimbooks.couponserver.domain.coupons.coupontype.enums.CouponTargetType;
import simsimbooks.couponserver.domain.coupons.coupontype.exception.CouponTypeNotFoundException;
import simsimbooks.couponserver.domain.coupons.coupontype.repository.CouponTypeRepository;
import simsimbooks.couponserver.domain.user.entity.User;
import simsimbooks.couponserver.domain.user.exception.UserNotFoundException;
import simsimbooks.couponserver.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponTypeRepository couponTypeRepository;
    private final UserRepository userRepository;

    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);
        return CouponMapper.toResponse(coupon);
    }

    public Page<CouponResponse> getCoupons(Long userId, CouponSearchCondition condition, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Page<Coupon> page = couponRepository.searchByUserId(user.getId(), condition, pageable);
        return page.map(CouponMapper::toResponse);
    }

    @Transactional
    @DistributedLock(target = LockTarget.COUPON_TYPE, key = "#couponTypeId")
    public CouponResponse issueCoupon(Long couponTypeId, Long userId) {
        CouponType couponType = couponTypeRepository.findById(couponTypeId).orElseThrow(CouponTypeNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        // 유저가 이미 미사용 쿠폰을 가지고 있으면 예외 발생
        if(couponRepository.existsUnusedCouponByUserId(user.getId(), couponType.getId())) throw new BusinessException(ErrorCode.USER_ALREADY_HAS_COUPON);

        couponType.issue();
        Coupon save = couponRepository.save(Coupon.of(user, couponType));

        return CouponMapper.toResponse(save);
    }

    /**
     * ADMIN 전용 메서드로, 다수의 엔드 유저에게 쿠폰을 일괄 발급한다.
     * 주로 생일 쿠폰, 새해 쿠폰과 같이 이벤트성 쿠폰을 발급할 때 사용된다.
     * 대량 발급의 경우 currentIssueCnt 값은 증가하지 않는다.
     */
    @Transactional
    public List<Long> bulkIssueCoupons(Long couponTypeId, List<Long> userIds) {
        int batchSize = 2000;
        // 발급된 유저 ID
        List<Long> issueUserIds = new ArrayList<>();
        for (int i = 0; i < userIds.size(); i += batchSize) {
            // 쿠폰 타입 조회
            // BulkInsert 후 em.clear 하기 때문에 반복문 내부에서 couponType 조회
            CouponType couponType = couponTypeRepository.findById(couponTypeId).orElseThrow(CouponTypeNotFoundException::new);
            // 이번 배치 처리 범위 계산
            int fromIndex = i;
            int toIndex = Math.min(i + batchSize, userIds.size());

            List<Long> batchUserIds = userIds.subList(fromIndex, toIndex);

            // 실제 존재하는 유저만 조회
            List<User> existingUsers = userRepository.findAllById(batchUserIds);
            // 쿠폰 생성
            List<Coupon> coupons = existingUsers.stream().map(id -> Coupon.of(id, couponType)).toList();
            // 쿠폰 일괄 저장
            // couponRepository.saveAllAndFlush(coupons);
            couponRepository.bulkInsert(coupons);

            // 발급된 유저 ID 추가
            issueUserIds.addAll(existingUsers.stream().map(User::getId).toList());
        }
        return issueUserIds;
    }

    /**
     * deadline이 지났는데 status가 아직 UNUSED인 쿠폰을 모두 만료처리한다.
     */
    @Transactional
    public void expireOverdueUnusedCoupon() {
        couponRepository.expireAllOverdueUnusedCoupon();
    }

    /**
     * 쿠폰을 사용한다.
     */
    @Transactional
    public void useCoupon(Long couponId, CouponTargetType targetType, Long targetId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);

        // 쿠폰이 이미 만료되었으면 예외
        if (coupon.getStatus() == CouponStatus.EXPIRED || coupon.getDeadline().isBefore(LocalDateTime.now())) {
            coupon.expire();
            throw new CouponAlreadyExpiredException();
        }
        // 쿠폰이 이미 사용되었으면 예외
        if (coupon.getStatus() == CouponStatus.USED) throw new CouponAlreadyUsedException();


        if(!coupon.isApplicable(targetType,targetId)) throw new CouponCannotApplyException();

        coupon.use();
    }
}
