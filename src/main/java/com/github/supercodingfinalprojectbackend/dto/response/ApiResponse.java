package com.github.supercodingfinalprojectbackend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
@Schema(title = "공통응답 폼")
@Deprecated(since = "v1.0.0")
public class ApiResponse<T> {
    @Schema(title = "요청 성공 여부")
    private boolean success;
    @Schema(title = "응답 상태 코드")
    private int status;
    @Schema(title = "응답 메세지")
    private String message;
    @Schema(title = "응답 데이터")
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, 200, "요청이 성공적으로 처리되었습니다.", data);
    }
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, 200, message, data);
    }
    public static <T> ApiResponse<T> success(T data, int status) {
        return new ApiResponse<>(true, status, "요청이 성공적으로 처리되었습니다.", data);
    }
    public static <T> ApiResponse<T> success(T data, int status, String message) {
        return new ApiResponse<>(true, status, message, data);
    }
    public ResponseEntity<ApiResponse<T>> toResponseEntity() {
        return ResponseEntity.status(status).body(this);
    }
    public static <T> ApiResponse<T> fail(int status, String message) {
        return new ApiResponse<>(false, status, message, null);
    }
}
