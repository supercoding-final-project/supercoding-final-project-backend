package com.github.supercodingfinalprojectbackend.security;

import com.github.supercodingfinalprojectbackend.exception.FilterExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final FilterExceptionHandler filterExceptionHandler;
    private final AuthorizationFilter authorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 세션 관리 정책 설정: 세션을 생성하지 않음
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/api/v1/**")
//                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                // 접근 권한 설정
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .antMatchers("/api/v1/test/**").authenticated()
                        .antMatchers("/api/v1/user/oauth2/kakao/logout", "/api/v1/user/switch/**").authenticated()
                        .anyRequest().permitAll()
                )
                // 필터 추가
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler, authorizationFilter.getClass())
                .build();
    }
}