package com.github.supercodingfinalprojectbackend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.*;

@Schema(title = "공통응답 폼")
public class ApiResponse<T> {
    @Schema(title = "요청 성공 여부")
    private final boolean success;
    @Schema(title = "응답 상태 코드")
    private final int status;
    @Schema(title = "응답 메세지")
    private final String message;
    @Schema(title = "응답 데이터")
    private final T data;

    public boolean isSuccess() { return success; }
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }

    public ApiResponse(HttpStatus status, String message, T data) {
        this(status.value(), message, data);
    }
    public ApiResponse(int status, String message, T data) {
        this.success = status >= 200 && status < 300;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseEntity<ApiResponse<T>> toResponseEntity() {
        return ResponseEntity.status(status).body(this);
    }

    public static <T> ApiResponse<T> ok(String message, T data) { return new ApiResponse<>(HttpStatus.OK, message, data); }
    public static <T> ApiResponse<T> created(String message, T data) { return new ApiResponse<>(HttpStatus.CREATED, message, data); }
    public static <T> ApiResponse<T> accepted(String message, T data) { return new ApiResponse<>(HttpStatus.ACCEPTED, message, data); }
    public static <T> ApiResponse<T> noContent(String message, T data) { return new ApiResponse<>(HttpStatus.NO_CONTENT, message, data); }
    public static <T> ApiResponse<T> movedPermanently(String message, T data) { return new ApiResponse<>(HttpStatus.MOVED_PERMANENTLY, message, data); }
    public static <T> ApiResponse<T> found(String message, T data) { return new ApiResponse<>(HttpStatus.FOUND, message, data); }
    public static <T> ApiResponse<T> notModified(String message, T data) { return new ApiResponse<>(HttpStatus.NOT_MODIFIED, message, data); }
    public static <T> ApiResponse<T> badRequest(String message, T data) { return new ApiResponse<>(HttpStatus.BAD_REQUEST, message, data); }
    public static <T> ApiResponse<T> unauthorized(String message, T data) { return new ApiResponse<>(HttpStatus.UNAUTHORIZED, message, data); }
    public static <T> ApiResponse<T> forbidden(String message, T data) { return new ApiResponse<>(HttpStatus.FORBIDDEN, message, data); }
    public static <T> ApiResponse<T> notFound(String message, T data) { return new ApiResponse<>(HttpStatus.NOT_FOUND, message, data); }
    public static <T> ApiResponse<T> conflict(String message, T data) { return new ApiResponse<>(HttpStatus.CONFLICT, message, data); }
    public static <T> ApiResponse<T> unprocessableEntity(String message, T data) { return new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY, message, data); }
    public static <T> ApiResponse<T> internalServerError(String message, T data) { return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, message, data); }
    public static <T> ApiResponse<T> serviceUnavailable(String message, T data) { return new ApiResponse<>(HttpStatus.SERVICE_UNAVAILABLE, message, data); }

    public static Builder status(int status) { return new Builder(status); }
    public static Builder status(HttpStatus status) { return new Builder(status); }
    public static Builder ok() { return new Builder(HttpStatus.OK); }
    public static Builder created() { return new Builder(HttpStatus.CREATED); }
    public static Builder accepted() { return new Builder(HttpStatus.ACCEPTED); }
    public static Builder noContent() { return new Builder(HttpStatus.NO_CONTENT); }
    public static Builder movedPermanently() { return new Builder(HttpStatus.MOVED_PERMANENTLY); }
    public static Builder found() { return new Builder(HttpStatus.FOUND); }
    public static Builder notModified() { return new Builder(HttpStatus.NOT_MODIFIED); }
    public static Builder badRequest() { return new Builder(HttpStatus.BAD_REQUEST); }
    public static Builder unauthorized() { return new Builder(HttpStatus.UNAUTHORIZED); }
    public static Builder forbidden() { return new Builder(HttpStatus.FORBIDDEN); }
    public static Builder notFound() { return new Builder(HttpStatus.NOT_FOUND); }
    public static Builder conflict() { return new Builder(HttpStatus.CONFLICT); }
    public static Builder unprocessableEntity() { return new Builder(HttpStatus.UNPROCESSABLE_ENTITY); }
    public static Builder internalServerError() { return new Builder(HttpStatus.INTERNAL_SERVER_ERROR); }
    public static Builder serviceUnavailable() { return new Builder(HttpStatus.SERVICE_UNAVAILABLE); }


    public static class Builder {
        private final int status;
        private String message;

        public Builder(int status) {
            this.status = status;
            this.message = "";
        }
        public Builder(HttpStatus status) {
            this.status = status.value();
            this.message = "";
        }
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        public <T> ApiResponse<T> build() {
            return body(null);
        }
        public <T> ApiResponse<T> body(T data) {
            return new ApiResponse<>(status, message, data);
        }
    }
}
