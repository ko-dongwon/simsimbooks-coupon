package simsimbooks.couponserver.domain.coupons.coupontype.controller;

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
import simsimbooks.couponserver.domain.coupons.coupontype.dto.CouponTypeCreateRequest;
import simsimbooks.couponserver.domain.coupons.coupontype.dto.CouponTypeResponse;
import simsimbooks.couponserver.domain.coupons.coupontype.dto.CouponTypeUpdateRequest;
import simsimbooks.couponserver.domain.coupons.coupontype.service.CouponTypeService;

@RestController
@RequiredArgsConstructor
public class CouponTypeController {
    private final CouponTypeService couponTypeService;

    @PostMapping("/coupon-types")
    public ResponseEntity<ApiResponse<CouponTypeResponse>> createCouponType(@Valid @RequestBody CouponTypeCreateRequest requestDto) {
        CouponTypeResponse response = couponTypeService.createCouponType(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response, "쿠폰 타입이 생성되었습니다."));
    }

    @GetMapping("/coupon-types/{couponTypeId}")
    public ResponseEntity<ApiResponse<CouponTypeResponse>> getCouponType(@PathVariable Long couponTypeId) {
        CouponTypeResponse response = couponTypeService.getCouponType(couponTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response, "쿠폰 타입 정보를 조회했습니다."));
    }

    @GetMapping("/coupon-types")
    public ResponseEntity<ApiResponse<Page<CouponTypeResponse>>> getCouponTypes(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CouponTypeResponse> page = couponTypeService.getCouponTypes(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(page, "쿠폰 타입 정보를 조회했습니다."));
    }

    /**
     * 쿠폰 정책 별 쿠폰 타입 페이징
     */
    @GetMapping("/coupon-policies/{couponPolicyId}/coupon-types")
    public ResponseEntity<ApiResponse<Page<CouponTypeResponse>>> getCouponTypesByCouponPolicy(@PathVariable Long couponPolicyId,
                                                                                              @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CouponTypeResponse> page = couponTypeService.getCouponTypesByCouponPolicy(couponPolicyId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(page, "쿠폰 정책 별 쿠폰 타입 정보를 조회했습니다."));
    }

    /**
     * 쿠폰 타입으로 발급된 쿠폰이 존재하면 삭제 불가능
     */
    @DeleteMapping("/coupon-types/{couponTypeId}")
    public ResponseEntity<ApiResponse<Void>> deleteCouponType(@PathVariable Long couponTypeId) {
        couponTypeService.deleteCouponType(couponTypeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(null, "쿠폰 타입 정보를 삭제했습니다."));
    }

    @PutMapping("/coupon-types/{couponTypeId}")
    public ResponseEntity<ApiResponse<CouponTypeResponse>> updateCouponType(@PathVariable Long couponTypeId,
                                                                            @RequestBody CouponTypeUpdateRequest requestDto) {
        CouponTypeResponse response = couponTypeService.updateCouponType(couponTypeId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response, "쿠폰 타입 정보를 수정했습니다."));
    }
}
