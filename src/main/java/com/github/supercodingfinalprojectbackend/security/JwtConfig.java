package com.github.supercodingfinalprojectbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProvider jwtProvider;
    @Bean
    public ProviderManager providerManager() {
        return new ProviderManager(jwtProvider);
    }
}