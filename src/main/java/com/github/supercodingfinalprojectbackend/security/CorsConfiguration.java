package com.github.supercodingfinalprojectbackend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedOriginPatterns("https://super-final-front.vercel.app", "http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:5173", "http://127.0.0.1:5173","http://3.34.5.246:4443")
                .allowedHeaders("Set-Cookie","loggedUser","Authorization","Access-Token-Expire-Time","authentication","Access-Control-Allow-Origin","Access-Control-Allow-Method","Access-Control-Allow-Headers")
//                .allowedOrigins("https://super-final-front.vercel.app", "http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:5173", "http://127.0.0.1:5173")
                .exposedHeaders("Set-Cookie","loggedUser","Authorization","Access-Token-Expire-Time","authentication")
                .allowCredentials(true);
    }
}