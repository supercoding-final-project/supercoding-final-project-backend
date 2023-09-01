package com.github.supercodingfinalprojectbackend.dto.response;

import org.aspectj.weaver.ast.Test;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

public class TestApiResponse<T> extends ResponseEntity<TestApiResponse.CommonResponse<T>> {

    public TestApiResponse(HttpStatus status) {
        super(status);
    }

    public TestApiResponse(CommonResponse<T> body, HttpStatus status) {
        super(body, status);
    }

    public TestApiResponse(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public TestApiResponse(CommonResponse<T> body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public TestApiResponse(CommonResponse<T> body, MultiValueMap<String, String> headers, int rawStatus) {
        super(body, headers, rawStatus);
    }

    public static class CommonResponse<T> {
        private boolean success;
        private int status;
        private String message;
        private T data;

        public CommonResponse(boolean success, int status, String message, T data) {
            this.success = success;
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public static <T> CommonResponse<T> success(T data, String message) {
            return new CommonResponse<>(true, 200, message, data);
        }

    }
}
