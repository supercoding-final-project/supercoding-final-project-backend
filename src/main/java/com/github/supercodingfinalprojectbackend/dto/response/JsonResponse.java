package com.github.supercodingfinalprojectbackend.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class JsonResponse<T> extends ResponseEntity<JsonResponse.JsonForm<T>> {
    public JsonResponse(JsonForm<T> body, MultiValueMap<String, String> headers, HttpStatus status) { super(body, headers, status); }
    public JsonResponse(JsonForm<T> body, MultiValueMap<String, String> headers, Integer status) { super(body, headers, status); }
    public static <T> JsonResponseBuilder<T> builder() { return new JsonResponseBuilder<>(); }

    public static class JsonForm<T> {
        private Boolean success;
        private Integer status;
        private String message;
        private T data;

        public JsonForm(Integer status, String message, T data) {
            this.success = status >= 200 && status < 300;
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public Boolean getSuccess() { return success; }
        public Integer getStatus() { return status; }
        public String getMessage() { return message; }
        public T getData() { return data; }
    }
}
