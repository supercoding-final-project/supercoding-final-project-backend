package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ImageFileDto {
    private String imageUrl;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class ImageUrlResponse {
        private String imageUrl;

        public static ImageUrlResponse from(String imageUrl) {
            return new ImageUrlResponse(imageUrl);
        }
    }
}
