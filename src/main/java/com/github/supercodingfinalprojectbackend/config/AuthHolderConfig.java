package com.github.supercodingfinalprojectbackend.config;

import com.github.supercodingfinalprojectbackend.dto.LoginInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AuthHolderConfig {

    @Bean("AuthHolder")
        public ConcurrentHashMap<String, LoginInfo> concurrentHashMap() {
        return new ConcurrentHashMap<>();
    }
}
