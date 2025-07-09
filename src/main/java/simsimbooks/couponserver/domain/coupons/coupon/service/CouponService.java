package simsimbooks.couponserver.domain.coupons.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simsimbooks.couponserver.common.exception.BusinessException;
import simsimbooks.couponserver.common.exception.ErrorCode;
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
    public CouponResponse issueCoupon(IssueCouponRequest requestDto) {
        CouponType couponType = couponTypeRepository.findById(requestDto.getCouponTypeId()).orElseThrow(CouponTypeNotFoundException::new);
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(UserNotFoundException::new);

        // 유저가 이미 미사용 쿠폰을 가지고 있으면 예외 발생
        if(couponRepository.existsUnusedCouponByUserId(user.getId())) throw new BusinessException(ErrorCode.USER_ALREADY_HAS_COUPON);

        couponType.issue();
        Coupon save = couponRepository.save(Coupon.of(user, couponType));

        return CouponMapper.toResponse(save);
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
