package com.github.supercodingfinalprojectbackend.security;

import com.github.supercodingfinalprojectbackend.exception.FilterExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
                .cors(Customizer.withDefaults())
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                // 접근 권한 설정
                .authorizeHttpRequests(matcherRegistry -> matcherRegistry
                        .antMatchers("/api/v1/test/").authenticated()
                        .antMatchers("/api/v1/auth/logout", "/api/v1/auth/switch/**").authenticated()
                        .antMatchers("/api/v1/users/role/join/mentor", "/api/v1/users/paymoney", "/api/v1/users/info").authenticated()
                        .antMatchers("/api/v1/mentors/info").authenticated()
                        .antMatchers("/api/v1/mentees/info").authenticated()
                        .antMatchers("/api/v1/orders/**").authenticated()
                        .antMatchers("/api/v1/createchat","/api/v1/chatrooms").authenticated()
                        .antMatchers("/api/v1/mentor/mypage/**","/api/v1/mentee/mypage/**").authenticated()
                        .antMatchers("/api/v1/events/identifier").authenticated()
                        .anyRequest().permitAll() // 다른 모든 요청을 허용하도록 설정
                )
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterExceptionHandler, authorizationFilter.getClass())
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://super-final-front.vercel.app", "http://127.0.0.1:5500", "http://127.0.0.1:5503","http://localhost:5503", "http://localhost:5173"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH","OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}