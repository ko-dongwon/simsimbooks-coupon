package simsimbooks.couponserver.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsimbooks.couponserver.common.dto.ApiResponse;
import simsimbooks.couponserver.domain.category.dto.CategoryCreateRequest;
import simsimbooks.couponserver.domain.category.dto.CategoryResponse;
import simsimbooks.couponserver.domain.category.dto.CategoryUpdateRequest;
import simsimbooks.couponserver.domain.category.service.CategoryService;

import java.util.Map;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String,Long>>> createCategory(@RequestBody CategoryCreateRequest requestDto) {
        Long id = categoryService.createCategory(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(Map.of("id", id), "카테고리가 생성되었습니다."));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategory(@PathVariable Long categoryId) {
        CategoryResponse response = categoryService.getCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response,"카테고리 정보를 조회했습니다."));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable Long categoryId,
                                                                        @RequestBody CategoryUpdateRequest requestDto) {
        CategoryResponse response = categoryService.updateCategory(categoryId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response, "카테고리 정보를 업데이트했습니다."));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null,"카테고리 정보를 삭제했습니다."));
    }


}
