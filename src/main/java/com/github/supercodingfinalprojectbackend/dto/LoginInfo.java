package com.github.supercodingfinalprojectbackend.dto;

import lombok.Getter;

@Getter
public class LoginInfo {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private String role;
}
