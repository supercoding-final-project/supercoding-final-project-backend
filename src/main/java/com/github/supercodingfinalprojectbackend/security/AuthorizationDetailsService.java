package com.github.supercodingfinalprojectbackend.security;

import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
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
    private final AuthHolder authHolder;

    @Override
    public AuthorizationDetails loadUserByUsername(String userIdString) throws UsernameNotFoundException {
        Long userId = ValidateUtils.requireNotThrow(()->Long.parseLong(userIdString), ApiErrorCode.UNRELIABLE_JWT);
        Login login = authHolder.get(userId);
        if (login == null) throw ApiErrorCode.UNRELIABLE_JWT.exception();
        String accessToken = login.getAccessToken();
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(login.getUserRole().name()));

        return AuthorizationDetails.builder()
                .userId(userIdString)
                .accessToken(accessToken)
                .authorities(authorities)
                .build();
    }
}

