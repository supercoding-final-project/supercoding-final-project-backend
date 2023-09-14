package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImageDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class UrlResponse {
        private String imageUrl;

        public static UrlResponse from(String imageUrl) {
            return new UrlResponse(imageUrl);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class UrlMapResponse {
        private Integer totalUrls;
        private Map<Integer, String> contents;

        public static UrlMapResponse from(ConcurrentHashMap<Integer, String> result) {
            return UrlMapResponse.builder()
                    .totalUrls(result.size())
                    .contents(result)
                    .build();
        }
    }
}
