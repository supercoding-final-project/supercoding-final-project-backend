package com.github.supercodingfinalprojectbackend.dto;

import lombok.Getter;

@Getter
public class LoginInfo {
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String role;
}
