package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.Kakao;
import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.MenteeAbstractAccount;
import com.github.supercodingfinalprojectbackend.entity.MenteeSocialInfo;
import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.entity.type.SocialPlatformType;
import com.github.supercodingfinalprojectbackend.repository.MenteeAbstractAccountRepository;
import com.github.supercodingfinalprojectbackend.repository.MenteeRepository;
import com.github.supercodingfinalprojectbackend.repository.MenteeSocialInfoRepository;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import com.github.supercodingfinalprojectbackend.security.JwtProvider;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
    private final JwtProvider jwtProvider;

    public ResponseEntity<?> kakaoLogin(String code) {
        Kakao.OauthToken kakaoOauthToken = getKakaoToken(code);
        Kakao.UserInfo kakaoUserInfo = getKakaoUserInfo(kakaoOauthToken);

        // 회원이 존재하지 않으면 회원 가입
        MenteeSocialInfo menteeSocialInfo = menteeSocialInfoRepository.findBySocialIdAndSocialPlatformAndIsDeletedIsFalse(kakaoUserInfo.getId(), SocialPlatformType.KAKAO)
                .orElseGet(()-> signupMenteeWithKakao(kakaoUserInfo));

        // TODO: 토큰을 만들어서 반환

        // 토큰에 들어갈 정보
        // 1. 유저 아이디
        // 2. 유저 역할
        // 3.

        // 1. 액세스 토큰 생성
        //      1-1. 유저 아이디, 액세스 토큰, 유저 역할
        // 2. 리프레쉬 토큰 생성
        //      2-1. 유저 아이디, 엑세스 토큰, 유저 역할

        Mentee mentee = menteeSocialInfo.getMentee();
        return ResponseUtils.ok("성공적으로 로그인 됨", mentee);
    }

    private MenteeSocialInfo signupMenteeWithKakao(Kakao.UserInfo kakaoUserInfo) {
        User newUser = User.builder()
                .name(kakaoUserInfo.getKakaoAccount().getName())
                .nickname(kakaoUserInfo.getKakaoAccount().getProfile().getNickName())
                .thumbnailImageUrl(kakaoUserInfo.getKakaoAccount().getProfile().getThumbnailImageUrl())
                .build();
        User savedUser = userRepository.save(newUser);

        MenteeAbstractAccount newMenteeAbstractAccount = MenteeAbstractAccount.builder()
                .accountNumber(createAccountNumber())
                .paymoney(0L)
                .build();
        MenteeAbstractAccount savedMenteeAbstractAccount = menteeAbstractAccountRepository.save(newMenteeAbstractAccount);

        Mentee newMentee = Mentee.builder()
                .abstractAccount(savedMenteeAbstractAccount)
                .user(savedUser)
                .build();
        Mentee savedMentee = menteeRepository.save(newMentee);

        MenteeSocialInfo newMenteeSocialInfo = MenteeSocialInfo.builder()
                .mentee(savedMentee)
                .socialId(kakaoUserInfo.getId())
                .socialPlatform(SocialPlatformType.KAKAO)
                .build();
        return menteeSocialInfoRepository.save(newMenteeSocialInfo);
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
        String AccountNumber = num1 + "-" + num2 + "-" + num3 + "-" + num4;
        System.out.println(AccountNumber);
        return AccountNumber ;
    }

    public Kakao.OauthToken getKakaoToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<?> request = createKakaoTokenRequest(code);
        ResponseEntity<Kakao.OauthToken> response = restTemplate.exchange(request, Kakao.OauthToken.class);
        Kakao.OauthToken kakaoOauthToken = Objects.requireNonNull(response.getBody());
        System.out.println(kakaoOauthToken);
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

    public Kakao.UserInfo getKakaoUserInfo(Kakao.OauthToken kakaoOauthToken) {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<?> request = createKakaoUserInfoRequest(kakaoOauthToken);
        ResponseEntity<Kakao.UserInfo> response = restTemplate.exchange(request, Kakao.UserInfo.class);
        Kakao.UserInfo userInfo = Objects.requireNonNull(response.getBody());
        System.out.println(userInfo);
        return userInfo;
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
