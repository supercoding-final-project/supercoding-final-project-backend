package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RefreshTokenDTO {
    private String refreshToken;
}
