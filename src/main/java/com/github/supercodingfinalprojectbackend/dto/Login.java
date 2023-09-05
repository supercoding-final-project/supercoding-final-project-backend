package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Login {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private String roleName;
}
