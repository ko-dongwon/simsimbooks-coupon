package simsimbooks.couponserver.domain.coupons.coupontype.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simsimbooks.couponserver.common.util.DtoMapper;
import simsimbooks.couponserver.domain.coupons.coupon.repository.CouponRepository;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.CouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.exception.CouponPolicyDeleteFailException;
import simsimbooks.couponserver.domain.coupons.couponpolicy.exception.CouponPolicyNotFoundException;
import simsimbooks.couponserver.domain.coupons.couponpolicy.repository.CouponPolicyRepository;
import simsimbooks.couponserver.domain.coupons.coupontype.dto.CouponTypeCreateRequest;
import simsimbooks.couponserver.domain.coupons.coupontype.dto.CouponTypeResponse;
import simsimbooks.couponserver.domain.coupons.coupontype.dto.CouponTypeUpdateRequest;
import simsimbooks.couponserver.domain.coupons.coupontype.entity.CouponType;
import simsimbooks.couponserver.domain.coupons.coupontype.exception.CouponTypeNotFoundException;
import simsimbooks.couponserver.domain.coupons.coupontype.repository.CouponTypeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponTypeService {
    private final CouponTypeRepository couponTypeRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public CouponTypeResponse createCouponType(CouponTypeCreateRequest requestDto) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(requestDto.getCouponPolicyId()).orElseThrow(CouponPolicyNotFoundException::new);
        CouponType couponType = CouponType.of(
                requestDto.getName(),
                requestDto.getPeriod(),
                requestDto.getDeadline(),
                couponPolicy,
                requestDto.getMaxIssueCnt(),
                requestDto.getTargetType(),
                requestDto.getTargetId()
        );
        CouponType save = couponTypeRepository.save(couponType);
        return DtoMapper.toDto(save, CouponTypeResponse.class);
    }

    @Transactional
    public CouponTypeResponse updateCouponType(Long couponTypeId, CouponTypeUpdateRequest requestDto) {
        CouponType couponType = couponTypeRepository.findById(couponTypeId).orElseThrow(CouponPolicyNotFoundException::new);
        couponType.updateName(requestDto.getName());
        couponType.updatePeriod(requestDto.getPeriod());
        couponType.updateDeadline(requestDto.getDeadline());
        couponType.updateMaxIssueCnt(requestDto.getMaxIssueCnt());
        return DtoMapper.toDto(couponType, CouponTypeResponse.class);
    }

    public CouponTypeResponse getCouponType(Long couponTypeId) {
        CouponType couponType = couponTypeRepository.findById(couponTypeId).orElseThrow(CouponPolicyNotFoundException::new);
        return DtoMapper.toDto(couponType, CouponTypeResponse.class);
    }

    public Page<CouponTypeResponse> getCouponTypes(Pageable pageable) {
        Page<CouponType> page = couponTypeRepository.findAll(pageable);
        return page.map(e -> DtoMapper.toDto(e, CouponTypeResponse.class));
    }

    public Page<CouponTypeResponse> getCouponTypesByCouponPolicy(Long couponPolicyId, Pageable pageable) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(couponPolicyId).orElseThrow(CouponPolicyNotFoundException::new);
        Page<CouponType> page = couponTypeRepository.findAllByCouponPolicy(couponPolicy, pageable);
        return page.map(e -> DtoMapper.toDto(e, CouponTypeResponse.class));
    }

    @Transactional
    public void deleteCouponType(Long couponTypeId) {
        CouponType couponType = couponTypeRepository.findById(couponTypeId).orElseThrow(CouponTypeNotFoundException::new);
        if(couponRepository.countByCouponType(couponType) != 0L) throw new CouponPolicyDeleteFailException("쿠폰 타입으로 발급된 쿠폰이 존재해 삭제할 수 없습니다.");
        couponTypeRepository.delete(couponType);
    }
}
