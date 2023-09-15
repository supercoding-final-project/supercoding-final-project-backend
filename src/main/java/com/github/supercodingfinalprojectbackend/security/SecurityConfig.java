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
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

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
                .csrf().disable()
                // CORS 허용
                .cors().configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("https://super-final-front.vercel.app", "http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:5173", "http://127.0.0.1:5173"));
                    config.setAllowedMethods(List.of("*"));
                    config.setAllowedHeaders(List.of("*"));
                    return config;
                })
                .and()
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                // 접근 권한 설정
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .antMatchers("/api/v1/test/**").authenticated()
                        .antMatchers("/api/v1/auth/logout", "/api/v1/auth/switch/**").authenticated()
                        .antMatchers("/api/v1/users/role/join/mentor", "/api/v1/users/paymoney").authenticated()
                        .antMatchers("/api/v1/mentors/info").authenticated()
                        .antMatchers("/api/v1/orders/**").authenticated()
                        .anyRequest().permitAll() // 다른 모든 요청을 허용하도록 설정
                )
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler, authorizationFilter.getClass())
                .build();
    }
}