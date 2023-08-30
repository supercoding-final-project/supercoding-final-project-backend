package com.github.supercodingfinalprojectbackend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class SecretKeyConfig {

    @Bean
    public SecretKey secretKey(@Value("${secret-key-source}") String secretKeySource) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] sha256SecretKeySource = digest.digest(secretKeySource.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(sha256SecretKeySource, "HmacSHA256");
    }

}
