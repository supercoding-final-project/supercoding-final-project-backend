package com.github.supercodingfinalprojectbackend.config;

import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.util.auth.AuthHolder;
import com.github.supercodingfinalprojectbackend.util.jwt.TokenHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthHolderConfig {

    @Bean("AuthHolder")
    public AuthHolder authHolder() {
        AuthHolder authHolder = new AuthHolder();
        Long superUserId = 1004L;
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsImF1dGhvcml0aWVzIjpbIk1FTlRPUiJdLCJpYXQiOjE2OTUwNDY4MDIsImV4cCI6MTcyNjU4MjgwMn0.CWUTO-umu495meKXmYSiIg1j1cFBJcaoIOK33FrZ67A";
        String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsImF1dGhvcml0aWVzIjpbIk1FTlRPUiJdLCJpYXQiOjE2OTUwNDY4MDIsImV4cCI6MjAxMDQwNjgwMn0.j4tjPhVe1YDhKoHWapebBPK5DvIv_KQTS4B4S0L5HFw";
        TokenHolder tokenHolder = new TokenHolder().putAccessToken(accessToken).putRefreshToken(refreshToken);
        authHolder.put(superUserId, Login.of(UserRole.MENTOR, tokenHolder));

        return authHolder;
    }
}
