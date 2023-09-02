package com.github.supercodingfinalprojectbackend.dto.response;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

public class JsonResponse<T> extends ResponseEntity<JsonResponse.JsonForm<T>> {
    public JsonResponse(JsonForm<T> body, MultiValueMap<String, String> headers, HttpStatus status) { super(body, headers, status); }
    public JsonResponse(JsonForm<T> body, MultiValueMap<String, String> headers, Integer status) { super(body, headers, status); }
    public static <T> JsonResponse.JsonResponseBuilder<T> builder() { return new JsonResponseBuilder<>(); }

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

    public static class JsonResponseBuilder<T> {
        private Integer status;
        private String message;
        private HttpHeaders headers = new HttpHeaders();
        private T data;
        public JsonResponseBuilder() {
            this.status = 500;
            this.message = "";
        }
        public JsonResponseBuilder<T> status(Integer status) {
            this.status = status;
            return this;
        }
        public JsonResponseBuilder<T> status(HttpStatus status) {
            this.status = status.value();
            return this;
        }
        public JsonResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }
        public JsonResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }
        public JsonResponseBuilder<T> header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return this;
        }
        public JsonResponseBuilder<T> headers(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }
        public JsonResponseBuilder<T> headers(Consumer<HttpHeaders> headersConsumer) {
            headersConsumer.accept(this.headers);
            return this;
        }
        public JsonResponseBuilder<T> allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
            return this;
        }
        public JsonResponseBuilder<T> contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }
        public JsonResponseBuilder<T> contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }
        public JsonResponseBuilder<T> eTag(String etag) {
            if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
                etag = "\"" + etag;
            }
            if (!etag.endsWith("\"")) {
                etag = etag + "\"";
            }
            this.headers.setETag(etag);
            return this;
        }
        public JsonResponseBuilder<T> lastModified(ZonedDateTime date) {
            this.headers.setLastModified(date);
            return this;
        }
        public JsonResponseBuilder<T> lastModified(Instant date) {
            this.headers.setLastModified(date);
            return this;
        }
        public JsonResponseBuilder<T> lastModified(long date) {
            this.headers.setLastModified(date);
            return this;
        }
        public JsonResponseBuilder<T> location(URI location) {
            this.headers.setLocation(location);
            return this;
        }
        public JsonResponseBuilder<T> cacheControl(CacheControl cacheControl) {
            this.headers.setCacheControl(cacheControl);
            return this;
        }
        public JsonResponseBuilder<T> varyBy(String... requestHeaders) {
            this.headers.setVary(Arrays.asList(requestHeaders));
            return this;
        }
        public JsonResponse<T> build() {
            JsonResponse.JsonForm<T> body = new JsonResponse.JsonForm<>(status, message, data);
            return new JsonResponse<>(body, headers, status);
        }
    }
}
