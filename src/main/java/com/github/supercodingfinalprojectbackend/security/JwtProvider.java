package com.github.supercodingfinalprojectbackend.security;

import com.github.supercodingfinalprojectbackend.dto.TokenHolder;
import com.github.supercodingfinalprojectbackend.exception.errorcode.JwtErrorCode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {

    private final SecretKey secretKey;
    private final AuthorizationDetailsService authorizationDetailsService;
    public static final String HEADER_NAME = HttpHeaders.AUTHORIZATION;


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
            throw JwtErrorCode.UNRELIABLE_JWT.exception();
        };

        return new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), details.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public boolean validateJwtToken(String jwt) {
        try {
            parseClaims(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            throw JwtErrorCode.EXPIRED_JWT.exception();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            throw JwtErrorCode.INVALID_SIGNATURE.exception();
        } catch (IllegalArgumentException e) {
            throw JwtErrorCode.EMPTY_JWT.exception();
        }
    }

    public UsernamePasswordAuthenticationToken parseAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        String userId = claims.getSubject();
        List<?> authorities = claims.get("authorities", List.class);
        if (authorities == null ) throw JwtErrorCode.INVALID_SIGNATURE.exception();
        Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(userId, accessToken, grantedAuthorities);
    }

    public TokenHolder createToken(String userId, Set<String> authorities) {
        Date now = new Date();
        final long oneHour = 3_600_000L;
        final long oneMonth = oneHour * 24 * 30;
        String accessToken = Jwts.builder()
                .setSubject(userId)
                .claim("authorities", authorities)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + oneHour))
                .signWith(secretKey)
                .compact();
        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .claim("access_token", accessToken)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + oneMonth))
                .signWith(secretKey)
                .compact();

        return new TokenHolder().putAccessToken(accessToken).putRefreshToken(refreshToken);
    }

    public String parseJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_NAME);
        if (bearerToken == null) return null;
        if (!bearerToken.startsWith("Bearer ")) return null;
        return bearerToken.substring("Bearer ".length());
    }
}
