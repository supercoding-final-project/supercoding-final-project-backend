package com.github.supercodingfinalprojectbackend.security;

import com.github.supercodingfinalprojectbackend.util.auth.AuthHolder;
import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorizationDetailsService implements UserDetailsService {

    @Qualifier("AuthHolder")
    private final AuthHolder<Long, Login> authHolder;

    @Override
    public AuthorizationDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        long userIdLong;
        try {
            userIdLong = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw ApiErrorCode.UNRELIABLE_JWT.exception();
        }
        Login login = authHolder.get(userIdLong);
        if (login == null) throw ApiErrorCode.UNRELIABLE_JWT.exception();
        String accessToken = login.getAccessToken();
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(login.getUserRole().name()));

        return AuthorizationDetails.builder()
                .userId(userId)
                .accessToken(accessToken)
                .authorities(authorities)
                .build();
    }
}

