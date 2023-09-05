package com.github.supercodingfinalprojectbackend.security;

import com.github.supercodingfinalprojectbackend.dto.AuthHolder;
import com.github.supercodingfinalprojectbackend.dto.LoginInfo;
import com.github.supercodingfinalprojectbackend.exception.errorcode.JwtErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthorizationDetailsService implements UserDetailsService {

    @Qualifier("AuthHolder")
    private final AuthHolder<String, LoginInfo> authHolder;

    @Override
    public AuthorizationDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        LoginInfo loginInfo = authHolder.get(userId);
        if (loginInfo == null) throw JwtErrorCode.UNRELIABLE_JWT.exception();
        String accessToken = loginInfo.getAccessToken();
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(loginInfo.getRoleName()));

        return AuthorizationDetails.builder()
                .userId(userId)
                .accessToken(accessToken)
                .authorities(authorities)
                .build();
    }
}

