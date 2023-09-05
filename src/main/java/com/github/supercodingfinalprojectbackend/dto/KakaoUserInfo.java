package com.github.supercodingfinalprojectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@ToString
public class KakaoUserInfo {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("has_signed_up")
    private Boolean hasSignedUp;
    @JsonProperty("connected_at")
    private Instant connetedAt;
    @JsonProperty("synched_at")
    private Instant synchedAt;
    @JsonProperty("properties")
    private KakaoProperty property;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;
    @JsonProperty("for_partner")
    private KakaoPartner partner;
}
