package com.github.supercodingfinalprojectbackend.config;

import com.github.supercodingfinalprojectbackend.dto.AuthHolder;
import com.github.supercodingfinalprojectbackend.dto.Login;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthHolderConfig {

    @Bean("AuthHolder")
    public AuthHolder<String, Login> authHolder() {
        return new AuthHolder<>();
    }
}
