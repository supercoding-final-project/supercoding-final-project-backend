package com.github.supercodingfinalprojectbackend.util;

import org.springframework.http.*;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

public class ResponseUtils {

    private ResponseUtils() {}

    public static <T> ResponseEntity<ApiResponse<T>> status(HttpStatus status, String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.OK, message, data);
        return new ResponseEntity<>(body, status);
    }
    public static <T> ResponseEntity<ApiResponse<T>> status(int status, String message, T data) {
        HttpStatus statusObj = HttpStatus.valueOf(status);
        ApiResponse<T> body = new ApiResponse<>(statusObj, message, data);
        return new ResponseEntity<>(body, statusObj);
    }
    public static <T> ResponseEntity<ApiResponse<T>> ok(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.OK, message, data);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    public static <T> ResponseEntity<ApiResponse<T>> created(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.CREATED, message, data);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }
    public static <T> ResponseEntity<ApiResponse<T>> accepted(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.ACCEPTED, message, data);
        return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
    }
    public static <T> ResponseEntity<ApiResponse<T>> noContent(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.NO_CONTENT, message, data);
        return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
    }
    public static <T> ResponseEntity<ApiResponse<T>> movedPermanently(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.MOVED_PERMANENTLY, message, data);
        return new ResponseEntity<>(body, HttpStatus.MOVED_PERMANENTLY);
    }
    public static <T> ResponseEntity<ApiResponse<T>> found(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.FOUND, message, data);
        return new ResponseEntity<>(body, HttpStatus.FOUND);
    }
    public static <T> ResponseEntity<ApiResponse<T>> notModified(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.NOT_MODIFIED, message, data);
        return new ResponseEntity<>(body, HttpStatus.NOT_MODIFIED);
    }
    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.BAD_REQUEST, message, data);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    public static <T> ResponseEntity<ApiResponse<T>> unauthorized(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.UNAUTHORIZED, message, data);
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
    public static <T> ResponseEntity<ApiResponse<T>> forbidden(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.FORBIDDEN, message, data);
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }
    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.NOT_FOUND, message, data);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    public static <T> ResponseEntity<ApiResponse<T>> conflict(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.CONFLICT, message, data);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    public static <T> ResponseEntity<ApiResponse<T>> unprocessableEntity(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.UNPROCESSABLE_ENTITY, message, data);
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    public static <T> ResponseEntity<ApiResponse<T>> internalServerError(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, message, data);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static <T> ResponseEntity<ApiResponse<T>> serviceUnavailable(String message, T data) {
        ApiResponse<T> body = new ApiResponse<>(HttpStatus.SERVICE_UNAVAILABLE, message, data);
        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }

    public static ApiResponseBuilder status(int status) { return new ApiResponseBuilder(status); }
    public static ApiResponseBuilder status(HttpStatus status) { return new ApiResponseBuilder(status); }
    public static ApiResponseBuilder ok() { return new ApiResponseBuilder(HttpStatus.OK); }
    public static ApiResponseBuilder created() { return new ApiResponseBuilder(HttpStatus.CREATED); }
    public static ApiResponseBuilder accepted() { return new ApiResponseBuilder(HttpStatus.ACCEPTED); }
    public static ApiResponseBuilder noContent() { return new ApiResponseBuilder(HttpStatus.NO_CONTENT); }
    public static ApiResponseBuilder movedPermanently() { return new ApiResponseBuilder(HttpStatus.MOVED_PERMANENTLY); }
    public static ApiResponseBuilder found() { return new ApiResponseBuilder(HttpStatus.FOUND); }
    public static ApiResponseBuilder notModified() { return new ApiResponseBuilder(HttpStatus.NOT_MODIFIED); }
    public static ApiResponseBuilder badRequest() { return new ApiResponseBuilder(HttpStatus.BAD_REQUEST); }
    public static ApiResponseBuilder unauthorized() { return new ApiResponseBuilder(HttpStatus.UNAUTHORIZED); }
    public static ApiResponseBuilder forbidden() { return new ApiResponseBuilder(HttpStatus.FORBIDDEN); }
    public static ApiResponseBuilder notFound() { return new ApiResponseBuilder(HttpStatus.NOT_FOUND); }
    public static ApiResponseBuilder conflict() { return new ApiResponseBuilder(HttpStatus.CONFLICT); }
    public static ApiResponseBuilder unprocessableEntity() { return new ApiResponseBuilder(HttpStatus.UNPROCESSABLE_ENTITY); }
    public static ApiResponseBuilder internalServerError() { return new ApiResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR); }
    public static ApiResponseBuilder serviceUnavailable() { return new ApiResponseBuilder(HttpStatus.SERVICE_UNAVAILABLE); }

    public static class ApiResponseBuilder {
        private final HttpStatus status;
        private String message;
        private HttpHeaders headers;

        public ApiResponseBuilder(int status) {
            this.status = HttpStatus.valueOf(status);
            this.message = "";
            this.headers = new HttpHeaders();
        }
        public ApiResponseBuilder(HttpStatus status) {
            this.status = status;
            this.message = "";
            this.headers = new HttpHeaders();
        }
        public ApiResponseBuilder message(String message) {
            this.message = message;
            return this;
        }
        public ApiResponseBuilder header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return this;
        }
        public ApiResponseBuilder headers(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }
        public ApiResponseBuilder headers(Consumer<HttpHeaders> headersConsumer) {
            headersConsumer.accept(this.headers);
            return this;
        }
        public ApiResponseBuilder allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
            return this;
        }
        public ApiResponseBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }
        public ApiResponseBuilder contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }
        public ApiResponseBuilder eTag(String etag) {
            if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
                etag = "\"" + etag;
            }
            if (!etag.endsWith("\"")) {
                etag = etag + "\"";
            }
            this.headers.setETag(etag);
            return this;
        }
        public ApiResponseBuilder lastModified(ZonedDateTime date) {
            this.headers.setLastModified(date);
            return this;
        }
        public ApiResponseBuilder lastModified(Instant date) {
            this.headers.setLastModified(date);
            return this;
        }
        public ApiResponseBuilder lastModified(long date) {
            this.headers.setLastModified(date);
            return this;
        }
        public ApiResponseBuilder location(URI location) {
            this.headers.setLocation(location);
            return this;
        }
        public ApiResponseBuilder cacheControl(CacheControl cacheControl) {
            this.headers.setCacheControl(cacheControl);
            return this;
        }
        public ApiResponseBuilder varyBy(String... requestHeaders) {
            this.headers.setVary(Arrays.asList(requestHeaders));
            return this;
        }
        public <T> ResponseEntity<ApiResponse<T>> build() {
            return body(null);
        }
        public <T> ResponseEntity<ApiResponse<T>> body(T data) {
            ApiResponse<T> apiResponse =  new ApiResponse<>(status.value(), message, data);
            return new ResponseEntity<>(apiResponse, headers, status);
        }
    }

    public static class ApiResponse<T> {
        private boolean success;
        private int status;
        private String message;
        private T data;

        public boolean isSuccess() { return success; }
        public int getStatus() { return status; }
        public String getMessage() { return message; }
        public T getData() { return data; }

        public ApiResponse(int status, String message, T data) {
            this.success = HttpStatus.valueOf(status).is2xxSuccessful();
            this.status = status;
            this.message = message;
            this.data = data;
        }
        public ApiResponse(HttpStatus status, String message, T data) {
            int statusInt = status.value();
            this.success = status.is2xxSuccessful();
            this.status = statusInt;
            this.message = message;
            this.data = data;
        }
    }
}
