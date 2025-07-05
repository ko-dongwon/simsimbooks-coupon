package simsimbooks.couponserver.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simsimbooks.couponserver.common.dto.ApiResponse;
import simsimbooks.couponserver.domain.user.dto.UserCreateRequest;
import simsimbooks.couponserver.domain.user.dto.UserResponse;
import simsimbooks.couponserver.domain.user.dto.UserUpdateRequest;
import simsimbooks.couponserver.domain.user.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping // Create
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserCreateRequest requestDto) {
        UserResponse response = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response,"회원이 생성되었습니다."));
    }

    @GetMapping("/{userId}")// Read
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long userId) {
        UserResponse response = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response,"회원 정보를 조회했습니다."));
    }

    @PatchMapping("/{userId}") // Update
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long userId,
                                                      @RequestBody UserUpdateRequest requestDto) {
        UserResponse response = userService.updateUser(userId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response,"회원 정보를 업데이트했습니다."));
    }

    @DeleteMapping("/{userId}") //Delete
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null,"회원 정보를 삭제했습니다."));
    }
}
