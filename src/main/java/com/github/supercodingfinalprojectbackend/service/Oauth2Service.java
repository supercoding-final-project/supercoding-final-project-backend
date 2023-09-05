package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.AuthHolder;
import com.github.supercodingfinalprojectbackend.dto.Kakao;
import com.github.supercodingfinalprojectbackend.dto.Login;
import com.github.supercodingfinalprojectbackend.dto.TokenHolder;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.entity.type.SocialPlatformType;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.errorcode.KakaoErrorCode;
import com.github.supercodingfinalprojectbackend.repository.*;
import com.github.supercodingfinalprojectbackend.security.JwtProvider;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    private final MenteeAbstractAccountRepository menteeAbstractAccountRepository;
    private final MenteeRepository menteeRepository;
    private final MenteeSocialInfoRepository menteeSocialInfoRepository;
    private final UserRepository userRepository;
    private final LoginRecordRepository loginRecordRepository;
    private final JwtProvider jwtProvider;
    @Qualifier("AuthHolder")
    private final AuthHolder<String, Login> authHolder;

    public Login kakaoLogin(String code) {
        Kakao.OauthToken kakaoOauthToken = getKakaoToken(code);
        Kakao.UserInfo kakaoUserInfo = getKakaoUserInfo(kakaoOauthToken);

        // 회원이 존재하지 않으면 회원 가입
        MenteeSocialInfo menteeSocialInfo = menteeSocialInfoRepository.findBySocialIdAndSocialPlatformAndIsDeletedIsFalse(kakaoUserInfo.getId(), SocialPlatformType.KAKAO)
                .orElseGet(()-> signupMenteeWithKakao(kakaoUserInfo));

        // 토큰을 만들어서 반환
        String userId = menteeSocialInfo.getMentee().getUser().getUserId().toString();
        Set<String> authorities = Set.of(UserRole.MENTEE);
        TokenHolder tokenHolder = jwtProvider.createToken(userId, authorities);

        // 메모리에 로그인 정보 저장
        Login login = Login.builder()
                .userId(userId)
                .roleName(UserRole.MENTEE)
                .accessToken(tokenHolder.getAccessToken())
                .refreshToken(tokenHolder.getRefreshToken())
                .build();
        authHolder.put(userId, login);

        // DB에 로그인 기록 저장
        LoginRecord loginRecord = LoginRecord.builder()
                .user(menteeSocialInfo.getMentee().getUser())
                .roleName(UserRole.MENTEE)
                .build();
        loginRecordRepository.save(loginRecord);

        return login;
    }

    private MenteeSocialInfo signupMenteeWithKakao(Kakao.UserInfo kakaoUserInfo) {
        String name = kakaoUserInfo.getKakaoAccount().getName();
        String nickname = kakaoUserInfo.getKakaoAccount().getProfile().getNickName();
        String thumbnailImageUrl = kakaoUserInfo.getKakaoAccount().getProfile().getThumbnailImageUrl();

        User user = createAndSaveUser(name, nickname, thumbnailImageUrl);
        MenteeAbstractAccount menteeAbstractAccount = createAndSaveMenteeAbstractAccount();
        Mentee mentee = createAndSaveMentee(user, menteeAbstractAccount);
        return createAndSaveMenteeSocialInfo(mentee, kakaoUserInfo.getId(), SocialPlatformType.KAKAO);
    }

    private MenteeSocialInfo createAndSaveMenteeSocialInfo(Mentee mentee, Long socialId, String socialPlatform) {
        MenteeSocialInfo newMenteeSocialInfo = MenteeSocialInfo.builder()
                .mentee(mentee)
                .socialId(socialId)
                .socialPlatform(socialPlatform)
                .build();
        return menteeSocialInfoRepository.save(newMenteeSocialInfo);
    }

    private Mentee createAndSaveMentee(User user, MenteeAbstractAccount menteeAbstractAccount) {
        Mentee newMentee = Mentee.builder()
                .abstractAccount(menteeAbstractAccount)
                .user(user)
                .build();
        return menteeRepository.save(newMentee);
    }

    private MenteeAbstractAccount createAndSaveMenteeAbstractAccount() {
        MenteeAbstractAccount newMenteeAbstractAccount = MenteeAbstractAccount.builder()
                .accountNumber(createAccountNumber())
                .paymoney(0L)
                .build();
        return menteeAbstractAccountRepository.save(newMenteeAbstractAccount);
    }

    private User createAndSaveUser(String name, String nickname, String thumbnailImageUrl) {
        User newUser = User.builder()
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

    public Kakao.OauthToken getKakaoToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<?> request = createKakaoTokenRequest(code);
        ResponseEntity<Kakao.OauthToken> response = restTemplate.exchange(request, Kakao.OauthToken.class);
        try {
            return Objects.requireNonNull(response.getBody());
        } catch (NullPointerException e) {
            throw KakaoErrorCode.FAIL_TO_RECEIVE_TOKEN.exception();
        }
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

    public Kakao.UserInfo getKakaoUserInfo(Kakao.OauthToken kakaoOauthToken) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<?> request = createKakaoUserInfoRequest(kakaoOauthToken);
        ResponseEntity<Kakao.UserInfo> response = restTemplate.exchange(request, Kakao.UserInfo.class);
        try {
            return Objects.requireNonNull(response.getBody());
        } catch (NullPointerException e) {
            throw KakaoErrorCode.NOT_FOUND_USER_INFO.exception();
        }
    }

    private RequestEntity<Void> createKakaoUserInfoRequest(Kakao.OauthToken kakaoOauthToken) {
        System.out.println(kakaoUserInfoUri);
        URI uri = URI.create(kakaoUserInfoUri);

        String accessToken = kakaoOauthToken.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return RequestEntity.get(uri).headers(headers).build();
    }
}
