package com.github.supercodingfinalprojectbackend.config;

import com.github.supercodingfinalprojectbackend.util.auth.AuthHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthHolderConfig {

    @Bean("AuthHolder")
    public AuthHolder authHolder() {
        return new AuthHolder();
    }
}
