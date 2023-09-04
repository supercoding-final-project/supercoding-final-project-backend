package com.github.supercodingfinalprojectbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AuthHolderConfig {

    @Bean("AuthHolder")
        public ConcurrentHashMap<Long, String> concurrentHashMap() {
        return new ConcurrentHashMap<>();
    }
}
