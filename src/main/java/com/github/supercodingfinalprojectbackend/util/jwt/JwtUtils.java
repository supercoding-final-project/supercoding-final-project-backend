package com.github.supercodingfinalprojectbackend.util.jwt;

import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import io.jsonwebtoken.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUtils {
    public static final String ACCESS_TOKEN_HEADER_NAME = HttpHeaders.AUTHORIZATION;
    public static final String PREFIX = "Bearer ";
    private JwtUtils() {}

    public static String createToken(Long userId, Set<String> authorities, long lifeMillis, SecretKey secretKey) {
        ValidateUtils.requireNotNull(userId, 500, "subject는 null일 수 없습니다.");
        ValidateUtils.requireNotNull(authorities, 500, "authorities는 null일 수 없습니다.");
        ValidateUtils.requireNotNull(secretKey, 500, "secretKey는 null일 수 없습니다.");
        ValidateUtils.requireTrue(lifeMillis > 0L, 500, "lifeMillis는 0이하일 수 없습니다.");

        Date now = new Date();
        return Jwts.builder()
                .claim("userId", userId)
                .claim("authorities", authorities)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + lifeMillis))
                .signWith(secretKey)
                .compact();
    }

    public static TokenHolder createTokens(Long userId, Set<String> authorities, SecretKey secretKey) {
        ValidateUtils.requireNotNull(userId, 500, "subject는 null일 수 없습니다.");
        Set<String> validAuthorities = ValidateUtils.requireNotNullElse(authorities, Set.of(UserRole.NONE.name()));
        ValidateUtils.requireNotNull(secretKey, 500, "secretKey는 null일 수 없습니다.");

        final long oneHour = 3_600_000L;
        final long twoMonth = oneHour * 24 * 60;
        String accessToken = createToken(userId, validAuthorities, oneHour, secretKey);
        String refreshToken = createToken(userId, validAuthorities, twoMonth, secretKey);

        return new TokenHolder().putAccessToken(accessToken).putRefreshToken(refreshToken);
    }

    public static String cutPrefix(String jwt) {
        ValidateUtils.requireNotNull(jwt, 500, "jwt는 null일 수 없습니다.");
        if (!jwt.startsWith(PREFIX)) return jwt;
        return jwt.substring(PREFIX.length());
    }

    public static String prefix(String jwt) {
        ValidateUtils.requireNotNull(jwt, 500, "jwt는 null일 수 없습니다.");

        if (jwt.startsWith(PREFIX)) return jwt;
        return PREFIX + jwt;
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String jwt = request.getHeader(ACCESS_TOKEN_HEADER_NAME);
        if (jwt == null) return null;
        return cutPrefix(jwt);
    }

    public static Claims parseClaims(String jwt, SecretKey secretKey) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            throw ApiErrorCode.EXPIRED_JWT.exception();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            throw ApiErrorCode.INVALID_SIGNATURE_JWT.exception();
        } catch (IllegalArgumentException e) {
            throw ApiErrorCode.EMPTY_JWT.exception();
        }
    }

    public static UsernamePasswordAuthenticationToken parseAuthentication(String jwt, SecretKey secretKey) {
        Claims claims = parseClaims(jwt, secretKey);
        Long userId = claims.get("userId", Long.class);
        List<?> authorities = claims.get("authorities", List.class);
        if (authorities == null ) throw ApiErrorCode.INVALID_SIGNATURE_JWT.exception();
        Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(userId.toString(), jwt, grantedAuthorities);
    }

    public static Long getUserId(String jwt, SecretKey secretKey) {
        Claims claims = ValidateUtils.requireNotThrow(jwt, t->JwtUtils.parseClaims(t, secretKey), 500, "jwt가 유효하지 않습니다.");
        return claims.get("userId", Long.class);
    }
}
