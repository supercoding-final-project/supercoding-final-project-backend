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
        Long superMentorUserId = 1004L;
        String superMentorAccessToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsImF1dGhvcml0aWVzIjpbIk1FTlRPUiJdLCJpYXQiOjE2OTUwNTExMjQsImV4cCI6MTcyNjU4NzEyNH0.sCThkhy4Xe8YGCa0jdho1TteZ-BtLMl_iQHQSAeLn_w";
        String superMentorAefreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwMDQsImF1dGhvcml0aWVzIjpbIk1FTlRPUiJdLCJpYXQiOjE2OTUwNTExMjQsImV4cCI6MjAxMDQxMTEyNH0.3z0CB1hd4vqV5glOJLz6yY_8McAcxfnp3utQqE2mX6g";
        TokenHolder superMentortokenHolder = new TokenHolder().putAccessToken(superMentorAccessToken).putRefreshToken(superMentorAefreshToken);
        authHolder.put(superMentorUserId, Login.of(UserRole.MENTOR, superMentortokenHolder));

        Long superMenteeUserId = 5252L;
        String superMenteeAccessToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjUyNTIsImF1dGhvcml0aWVzIjpbIk1FTlRFRSJdLCJpYXQiOjE2OTUwNTExMjQsImV4cCI6MTcyNjU4NzEyNH0.v0ly5U3mVe15JyctMOHxBT_YZUZev5szX623gy1ND8s";
        String superMenteeAefreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjUyNTIsImF1dGhvcml0aWVzIjpbIk1FTlRFRSJdLCJpYXQiOjE2OTUwNTExMjQsImV4cCI6MjAxMDQxMTEyNH0.KBfjImIIeaGwxhOdLuHVdU80zU6VXWDnq3VwzeFtQBE";
        TokenHolder superMenteetokenHolder = new TokenHolder().putAccessToken(superMenteeAccessToken).putRefreshToken(superMenteeAefreshToken);
        authHolder.put(superMenteeUserId, Login.of(UserRole.MENTEE, superMenteetokenHolder));

        return authHolder;
    }
}
