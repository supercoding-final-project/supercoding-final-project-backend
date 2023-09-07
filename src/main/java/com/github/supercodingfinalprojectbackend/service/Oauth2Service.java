package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.AuthHolder;
import com.github.supercodingfinalprojectbackend.dto.Kakao;
import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.dto.TokenHolder;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.entity.type.SocialPlatformType;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.exception.errorcode.KakaoErrorCode;
import com.github.supercodingfinalprojectbackend.exception.errorcode.UserErrorCode;
import com.github.supercodingfinalprojectbackend.repository.*;
import com.github.supercodingfinalprojectbackend.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class Oauth2Service {

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;
    private final String kakaoGrantType = "authorization_code";
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUri;
    @Value("${spring.security.oauth2.client.provider.kakao.logout-uri}")
    private String kakaoLogoutUri;

    private final MenteeRepository menteeRepository;
    private final UserRepository userRepository;
    private final LoginRecordRepository loginRecordRepository;
    private final UserSocialInfoRepository userSocialInfoRepository;
    private final UserAbstractAccountRepository userAbstractAccountRepository;
    private final MentorRepository mentorRepository;
    private final JwtProvider jwtProvider;
    @Qualifier("AuthHolder")
    private final AuthHolder<Long, Login> authHolder;

    public Login kakaoLogin(String code) {
        Kakao.OauthToken kakaoOauthToken = getKakaoToken(code);
        Kakao.UserInfo kakaoUserInfo = getKakaoUserInfo(kakaoOauthToken);

        // 회원이 존재하지 않으면 회원 가입
        Long kakaoId = kakaoUserInfo.getId();
        UserSocialInfo userSocialInfo = userSocialInfoRepository.findBySocialIdAndSocialPlatformNameAndIsDeletedIsFalse(kakaoId, SocialPlatformType.KAKAO.name())
                .orElseGet(()->signupWithKakao(kakaoUserInfo));

        return serviceLogin(userSocialInfo, kakaoOauthToken.getAccessToken(), kakaoOauthToken.getRefreshToken(), SocialPlatformType.KAKAO);
    }

    public Login serviceLogin(UserSocialInfo userSocialInfo, String socialAccessToken, String socialRefreshToken, SocialPlatformType socialPlatformType) {
        // 이전 로그인 기록을 뒤져서 어떤 역할로 로그인할 것인지 선택
        User user = userSocialInfo.getUser();
        LoginRecord loginRecord = loginRecordRepository.findFirstByUserAndIsDeletedIsFalseOrderByCreatedAtDesc(user).orElse(null);
        UserRole userRole = loginRecord == null ? UserRole.MENTEE : UserRole.valueOf(loginRecord.getRoleName());

        // 토큰 생성
        Long userId = user.getUserId();
        String userIdString = userId.toString();
        Set<String> authorities = Set.of(userRole.name());
        TokenHolder tokenHolder = jwtProvider.createToken(userIdString, authorities);

        // 메모리에 로그인 정보 저장
        Login login = Login.builder()
                .userRole(userRole)
                .accessToken(tokenHolder.getAccessToken())
                .refreshToken(tokenHolder.getRefreshToken())
                .socialPlatformType(socialPlatformType)
                .socialAccessToken(socialAccessToken)
                .socialRefreshToken(socialRefreshToken)
                .build();
        authHolder.put(userId, login);

        // DB에 로그인 기록 저장
        LoginRecord newloginRecord = LoginRecord.builder()
                .user(user)
                .roleName(userRole.name())
                .build();
        loginRecordRepository.save(newloginRecord);

        return login;
    }

    private UserSocialInfo signupWithKakao(Kakao.UserInfo kakaoUserInfo) {
        Kakao.Account account = kakaoUserInfo.getKakaoAccount();
        Kakao.Profile profile = account.getProfile();
        String name = account.getName();
        String nickname = profile.getNickName();
        String thumbnailImageUrl = profile.getThumbnailImageUrl();
        Long socialId = kakaoUserInfo.getId();

        UserAbstractAccount savedAbstractAccount = createAndSaveUserAbstractAccount();
        User savedUser = createAndSaveUser(savedAbstractAccount, name, nickname, thumbnailImageUrl);
        UserSocialInfo savedUserSocialInfo = createAndSaveUserSocialInfo(savedUser, socialId, SocialPlatformType.KAKAO);
        createAndSaveMentee(savedUser);

        return savedUserSocialInfo;
    }

    private UserAbstractAccount createAndSaveUserAbstractAccount() {
        UserAbstractAccount newAbstractAccount = UserAbstractAccount.builder()
                .accountNumber(createAccountNumber())
                .paymoney(0L)
                .build();
        return userAbstractAccountRepository.save(newAbstractAccount);
    }

    private UserSocialInfo createAndSaveUserSocialInfo(User user, Long socialId, SocialPlatformType socialPlatformType) {
        UserSocialInfo newUserSocialInfo = UserSocialInfo.builder()
                .user(user)
                .socialId(socialId)
                .socialPlatformName(socialPlatformType.name())
                .build();
        return userSocialInfoRepository.save(newUserSocialInfo);
    }

    private Mentee createAndSaveMentee(User user) {
        Mentee newMentee = Mentee.builder()
                .user(user)
                .build();
        return menteeRepository.save(newMentee);
    }

    private User createAndSaveUser(UserAbstractAccount abstractAccount, String name, String nickname, String thumbnailImageUrl) {
        User newUser = User.builder()
                .abstractAccount(abstractAccount)
                .name(name)
                .nickname(nickname)
                .thumbnailImageUrl(thumbnailImageUrl)
                .build();
        return userRepository.save(newUser);
    }

    private String createAccountNumber() {
        long seed = Instant.now().toEpochMilli();
        Random random = new Random(seed);
        int min = 0x1000;
        int max = 0xffff;

        String num1 = Integer.toHexString(random.nextInt(max - min + 1) + min);
        long seconds = seed / 1000;    // 초 단위
        long time = seconds % LocalDateTime.of(2070, 1, 1, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);    // 0 ~ 99년 12월 31일 23시 59분 59초
        String num2 = String.format("%08x", time);
        String num3 = Integer.toHexString(random.nextInt(max - min + 1) + min);
        String num4 = Integer.toHexString(random.nextInt(max - min + 1) + min);
        return num1 + "-" + num2 + "-" + num3 + "-" + num4;
    }

    private Kakao.OauthToken getKakaoToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<?> request = createKakaoTokenRequest(code);
        ResponseEntity<Kakao.OauthToken> response = restTemplate.exchange(request, Kakao.OauthToken.class);
        Kakao.OauthToken kakaoOauthToken = response.getBody();
        if (kakaoOauthToken == null) throw KakaoErrorCode.FAIL_TO_RECEIVE_TOKEN.exception();
        return kakaoOauthToken;
    }

    private RequestEntity<MultiValueMap<String, String>> createKakaoTokenRequest(String code) {
        URI uri = URI.create(kakaoTokenUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", kakaoGrantType);
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);

        return RequestEntity.post(uri).headers(headers).body(body);
    }

    private Kakao.UserInfo getKakaoUserInfo(Kakao.OauthToken kakaoOauthToken) {
        RequestEntity<?> request = createKakaoUserInfoRequest(kakaoOauthToken);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Kakao.UserInfo> response = restTemplate.exchange(request, Kakao.UserInfo.class);

        Kakao.UserInfo kakaoUserInfo = response.getBody();
        if (kakaoUserInfo == null) throw KakaoErrorCode.NOT_FOUND_USER_INFO.exception();
        return kakaoUserInfo;
    }

    private RequestEntity<Void> createKakaoUserInfoRequest(Kakao.OauthToken kakaoOauthToken) {
        URI uri = URI.create(kakaoUserInfoUri);

        String accessToken = kakaoOauthToken.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return RequestEntity.get(uri).headers(headers).build();
    }

    public void kakaoLogout() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw ApiErrorCode.NOT_AUTHENTICATED.exception();

        Long userId = Long.valueOf((String) auth.getPrincipal());
        Login login = authHolder.get(userId);
        if (login == null) throw UserErrorCode.ALREADY_LOGGED_OUT.exception();

        URI uri = URI.create(kakaoLogoutUri);
        String kakaoAccessToken = login.getKakaoAccessToken();
        if (kakaoAccessToken == null) throw UserErrorCode.IS_NOT_LOGGED_IN_KAKAO.exception();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        RequestEntity<?> request = RequestEntity.get(uri).headers(headers).build();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(request, Object.class);
    }

    public void serviceLogout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw ApiErrorCode.NOT_AUTHENTICATED.exception();

        Long userId = Long.valueOf((String) auth.getPrincipal());
        authHolder.remove(userId);
    }

    public Login switchRole(Long userId, UserRole userRole) {
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(UserErrorCode.NOT_FOUND_USER::exception);
        Mentor mentor = mentorRepository.findByUserAndIsDeletedIsFalse(user).orElseThrow(UserErrorCode.NOT_FOUND_MENTOR::exception);

        Login login = authHolder.get(userId);
        if (!login.getUserRole().equals(userRole)) {
            login = switchLogin(user, login, userRole);
        }

        return login;
    }

    private Login switchLogin(User user, Login existsLogin, UserRole userRole) {
        LoginRecord newloginRecord = LoginRecord.builder()
                .user(user)
                .roleName(userRole.name())
                .build();
        loginRecordRepository.save(newloginRecord);

        Set<String> authorities = Set.of(userRole.name());
        TokenHolder tokenHolder = jwtProvider.createToken(user.getUserId().toString(), authorities);
        Login newLogin = Login.builder()
                .accessToken(tokenHolder.getAccessToken())
                .refreshToken(tokenHolder.getRefreshToken())
                .userRole(userRole)
                .socialAccessToken(existsLogin.getSocialAccessToken())
                .socialRefreshToken(existsLogin.getSocialRefreshToken())
                .socialPlatformType(existsLogin.getSocialPlatformType())
                .build();
        authHolder.put(user.getUserId(), newLogin);

        return newLogin;
    }
}
