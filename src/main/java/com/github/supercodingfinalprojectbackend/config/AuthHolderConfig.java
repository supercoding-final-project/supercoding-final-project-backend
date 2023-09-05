package com.github.supercodingfinalprojectbackend.config;

import com.github.supercodingfinalprojectbackend.dto.AuthHolder;
import com.github.supercodingfinalprojectbackend.dto.LoginInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthHolderConfig {

    @Bean("AuthHolder")
    public AuthHolder<String, LoginInfo> authHolder() {
        return new AuthHolder<>();
    }
}
