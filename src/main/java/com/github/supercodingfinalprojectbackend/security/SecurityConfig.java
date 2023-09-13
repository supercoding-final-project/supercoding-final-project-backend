package com.github.supercodingfinalprojectbackend.security;

import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.FilterExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 세션 관리 정책 설정: 세션을 생성하지 않음
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf().disable()
                .cors()
                .and()
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/api/v1/**")
//                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                // 접근 권한 설정
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .antMatchers("/api/v1/test/**").authenticated()
                        .antMatchers("/api/v1/auth/logout/kakao/", "/api/v1/user/switch/**", "/api/v1/user/role/join/mentor").authenticated()
                        .antMatchers(HttpMethod.POST, "/api/v1/order/approve").hasRole(UserRole.MENTOR.resolve().name())
                        .anyRequest().permitAll() // 다른 모든 요청을 허용하도록 설정
                )
                .build();
    }
}