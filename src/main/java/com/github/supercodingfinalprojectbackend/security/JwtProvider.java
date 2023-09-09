package com.github.supercodingfinalprojectbackend.security;

import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {

    private final AuthorizationDetailsService authorizationDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = (String) authentication.getPrincipal();
        String accessToken = (String) authentication.getCredentials();
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        AuthorizationDetails details = authorizationDetailsService.loadUserByUsername(userId);

        if (
                !details.getPassword().equals(accessToken) ||
                details.getAuthorities().size() != authorities.size() ||
                !details.getAuthorities().containsAll(authorities)
        ) {
            throw ApiErrorCode.UNRELIABLE_JWT.exception();
        };

        return new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), details.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
