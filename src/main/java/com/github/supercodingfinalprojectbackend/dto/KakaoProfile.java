package com.github.supercodingfinalprojectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoProfile {
    @JsonProperty("nickname")
    private String nickName;
    @JsonProperty("thumbnail_image_url")
    private String thumbnailImageUrl;
    @JsonProperty("profile_image_url")
    private String profileImageUrl;
    @JsonProperty("is_default_image")
    private Boolean isDefaultImage;
}
