package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

public class MenteeDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class MenteeInfoRequest {
        private String email;
        private String nickname;
        private String thumbnailImageUrl;

        public boolean validate() {
            return nickname != null;
        }
    }
}
