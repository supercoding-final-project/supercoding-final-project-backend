package com.github.supercodingfinalprojectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoPartner {
    @JsonProperty("uuid")
    private String uuid;
}
