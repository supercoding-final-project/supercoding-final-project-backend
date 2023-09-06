package com.github.supercodingfinalprojectbackend.security;

import com.github.supercodingfinalprojectbackend.dto.AuthHolder;
import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.exception.errorcode.JwtErrorCode;
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
            throw JwtErrorCode.UNRELIABLE_JWT.exception();
        }
        Login login = authHolder.get(userIdLong);
        if (login == null) throw JwtErrorCode.UNRELIABLE_JWT.exception();
        String accessToken = login.getAccessToken();
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(login.getRoleName()));

        return AuthorizationDetails.builder()
                .userId(userId)
                .accessToken(accessToken)
                .authorities(authorities)
                .build();
    }
}

