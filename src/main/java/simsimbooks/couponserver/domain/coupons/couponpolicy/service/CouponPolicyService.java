package simsimbooks.couponserver.domain.coupons.couponpolicy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simsimbooks.couponserver.domain.coupons.couponpolicy.dto.CouponPolicyCreateRequest;
import simsimbooks.couponserver.domain.coupons.couponpolicy.dto.CouponPolicyResponse;
import simsimbooks.couponserver.domain.coupons.couponpolicy.entity.CouponPolicy;
import simsimbooks.couponserver.domain.coupons.couponpolicy.exception.CouponPolicyDeleteFailException;
import simsimbooks.couponserver.domain.coupons.couponpolicy.exception.CouponPolicyNotFoundException;
import simsimbooks.couponserver.domain.coupons.couponpolicy.repository.CouponPolicyRepository;
import simsimbooks.couponserver.domain.coupons.couponpolicy.util.CouponPolicyMapper;
import simsimbooks.couponserver.domain.coupons.coupontype.repository.CouponTypeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponPolicyService {
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponTypeRepository couponTypeRepository;

    @Transactional
    public CouponPolicyResponse createCouponPolicy(CouponPolicyCreateRequest requestDto) {
        CouponPolicy save = couponPolicyRepository.save(CouponPolicyMapper.toEntity(requestDto));
        return CouponPolicyMapper.toResponse(save);
    }

    public Page<CouponPolicyResponse> getCouponPolicies(Pageable pageable) {
        Page<CouponPolicy> page = couponPolicyRepository.findAll(pageable);
        return page.map(CouponPolicyMapper::toResponse);
    }

    public CouponPolicyResponse getCouponPolicy(Long couponPolicyId) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(couponPolicyId).orElseThrow(CouponPolicyNotFoundException::new);
        return CouponPolicyMapper.toResponse(couponPolicy);
    }

    @Transactional
    public void deleteCouponPolicy(Long couponPolicyId) {
        CouponPolicy couponPolicy = couponPolicyRepository.findById(couponPolicyId).orElseThrow(CouponPolicyNotFoundException::new);
        // 쿠폰 정책으로 생성된 쿠폰 타입있는지 확인
        if(couponTypeRepository.countByCouponPolicy(couponPolicy) != 0L) throw new CouponPolicyDeleteFailException("쿠폰 정책으로 생성된 쿠폰 타입이 존재해 삭제할 수 없습니다.");
        couponPolicyRepository.delete(couponPolicy);

    }
}
