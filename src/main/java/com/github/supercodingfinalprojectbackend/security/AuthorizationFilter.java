package com.github.supercodingfinalprojectbackend.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final ProviderManager providerManager;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtProvider.parseJwt(request);

        if (jwt != null) {
            Authentication beforeAuthenticate = jwtProvider.parseAuthentication(jwt);
            Authentication afterAuthenticate = providerManager.authenticate(beforeAuthenticate);
            SecurityContextHolder.getContext().setAuthentication(afterAuthenticate);
        }

        doFilter(request, response, filterChain);
    }
}
