package simsimbooks.couponserver.domain.coupons.couponpolicy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsimbooks.couponserver.common.dto.ApiResponse;
import simsimbooks.couponserver.domain.coupons.couponpolicy.dto.CouponPolicyCreateRequest;
import simsimbooks.couponserver.domain.coupons.couponpolicy.dto.CouponPolicyResponse;
import simsimbooks.couponserver.domain.coupons.couponpolicy.service.CouponPolicyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon-policies")
public class CouponPolicyController {
    private final CouponPolicyService couponPolicyService;

    @PostMapping
    public ResponseEntity<ApiResponse<CouponPolicyResponse>> createCouponPolicy(@Valid @RequestBody CouponPolicyCreateRequest requestDto) {
        CouponPolicyResponse response = couponPolicyService.createCouponPolicy(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response,"쿠폰 정책이 생성되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CouponPolicyResponse>>> getCouponPolicies(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CouponPolicyResponse> page = couponPolicyService.getCouponPolicies(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(page, "쿠폰 정책 정보를 조회했습니다."));
    }

    //TODO
    @GetMapping("/{couponPolicyId}")
    public ResponseEntity<ApiResponse<CouponPolicyResponse>> getCouponPolicy(@PathVariable Long couponPolicyId) {
        CouponPolicyResponse response = couponPolicyService.getCouponPolicy(couponPolicyId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response, "쿠폰 정책 정보를 조회했습니다."));
    }

    //TODO
    @DeleteMapping("/{couponPolicyId}")
    public ResponseEntity<ApiResponse<Void>> deleteCouponPolicy(@PathVariable Long couponPolicyId) {
        couponPolicyService.deleteCouponPolicy(couponPolicyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null, "쿠폰 정책 정보를 삭제했습니다."));
    }
}
