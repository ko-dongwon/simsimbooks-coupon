package simsimbooks.couponserver.domain.coupons.coupon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsimbooks.couponserver.common.dto.ApiResponse;
import simsimbooks.couponserver.domain.coupons.coupon.dto.CouponResponse;
import simsimbooks.couponserver.domain.coupons.coupon.dto.CouponSearchCondition;
import simsimbooks.couponserver.domain.coupons.coupon.dto.IssueCouponRequest;
import simsimbooks.couponserver.domain.coupons.coupon.service.CouponService;

@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<ApiResponse<CouponResponse>> getCoupon(@PathVariable Long couponId) {
        CouponResponse response = couponService.getCoupon(couponId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response, "쿠폰 정보를 조회했습니다."));
    }

    /**
     * 유저별 쿠폰 페이지 조회
     * 쿠폰 상태와 적용 타입에 대한 조건을 줄 수 있음.
     */
    @GetMapping("/users/{userId}/coupons")
    public ResponseEntity<ApiResponse<Page<CouponResponse>>> getCoupons(@PathVariable Long userId,
                                                                        @RequestBody CouponSearchCondition condition,
                                                                        Pageable pageable) {
        Page<CouponResponse> page = couponService.getCoupons(userId,condition, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(page, "사용자 쿠폰 정보를 조회했습니다."));
    }

    /**
     * 유저에게 쿠폰 발급
     */
    @PostMapping("/coupons/issue")
    public ResponseEntity<ApiResponse<CouponResponse>> issueCoupon(@Valid @RequestBody IssueCouponRequest requestDto) {
        CouponResponse response = couponService.issueCoupon(requestDto.getCouponTypeId(), requestDto.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response, "쿠폰이 발급되었습니다."));
    }
}
