package simsimbooks.couponserver.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private List<String> messages;
    private T data;

    private LocalDateTime timestamp;// 응답 시간

    private ApiResponse(boolean success, T data, List<String> messages) {
        this.success = success;
        this.messages = messages;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data, List<String> messages) {
        return new ApiResponse<>(true, data, messages);
    }
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, List.of(message));
    }

    public static <T> ApiResponse<T> error(List<String> messages) {
        return new ApiResponse<>(false, null, messages);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, null, List.of(message));
    }
}