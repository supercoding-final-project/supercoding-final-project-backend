package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.*;
import com.github.supercodingfinalprojectbackend.entity.*;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.entity.type.SocialPlatformType;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.*;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthHolder;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import com.github.supercodingfinalprojectbackend.util.jwt.JwtUtils;
import com.github.supercodingfinalprojectbackend.util.jwt.TokenHolder;
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

import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

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
    private final SkillStackRepository skillStackRepository;
    private final MentorSkillStackRepository mentorSkillStackRepository;
    private final MentorCareerRepository mentorCareerRepository;
    private final SecretKey secretKey;
    @Qualifier("AuthHolder")
    private final AuthHolder authHolder;

    public Login kakaoLogin(String code) {
        ValidateUtils.requireNotNull(code, 401, "카카오 로그인에 실패했습니다.");

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
        ValidateUtils.requireNotNull(userSocialInfo, 500, "userSocialInfo는 null일 수 없습니다.");
        User user = userSocialInfo.getUser();
        ValidateUtils.requireNotNull(user, 500, "user는 null일 수 없습니다.");
        LoginRecord loginRecord = loginRecordRepository.findFirstByUserAndIsDeletedIsFalseOrderByCreatedAtDesc(user).orElse(null);
        UserRole userRole = loginRecord == null ? UserRole.MENTEE : UserRole.valueOf(loginRecord.getRoleName());

        // 토큰 생성
        Long userId = user.getUserId();
        String userIdString = userId.toString();
        Set<String> authorities = Set.of(userRole.name());
        TokenHolder tokenHolder = JwtUtils.createTokens(userIdString, authorities, secretKey);

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
        ValidateUtils.requireNotNull(kakaoUserInfo, 500, "kakaoUserInfo는 null일 수 없습니다.");
        Kakao.Account account = kakaoUserInfo.getKakaoAccount();

        ValidateUtils.requireNotNull(account, 500, "account는 null일 수 없습니다.");
        Kakao.Profile profile = account.getProfile();
        String name = account.getName();

        ValidateUtils.requireNotNull(profile, 500, "profile은 null일 수 없습니다.");
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
        ValidateUtils.requireNotNull(code, 500, "code는 null일 수 없습니다.");

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<MultiValueMap<String, String>> request = createKakaoTokenRequest(code);
        ResponseEntity<Kakao.OauthToken> response = ValidateUtils.requireApply(request, o->restTemplate.exchange(o, Kakao.OauthToken.class), 500, "카카오 토큰 요청에 실패했습니다.");
        Kakao.OauthToken kakaoOauthToken = response.getBody();
        return ValidateUtils.requireNotNull(kakaoOauthToken, 500, "카카오 토큰 요청에 실패했습니다.");
    }

    private RequestEntity<MultiValueMap<String, String>> createKakaoTokenRequest(String code) {
        ValidateUtils.requireNotNull(code, 500, "code는 null일 수 없습니다.");

        URI uri = ValidateUtils.requireApply(kakaoTokenUri, URI::create, 500,"카카오 토큰 요청에 실패했습니다.");
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
        ResponseEntity<Kakao.UserInfo> response = ValidateUtils.requireApply(request, r->restTemplate.exchange(r, Kakao.UserInfo.class), 500, "카카오 유저 정보 요청에 실패했습니다.");

        Kakao.UserInfo kakaoUserInfo = response.getBody();
        if (kakaoUserInfo == null) throw ApiErrorCode.NOT_FOUND_USER_INFO.exception();
        return ValidateUtils.requireNotNull(kakaoUserInfo, 500, "카카오 유저 정보를 불러오는 데 실패했습니다.");
    }

    private RequestEntity<Void> createKakaoUserInfoRequest(Kakao.OauthToken kakaoOauthToken) {

        URI uri = ValidateUtils.requireApply(kakaoUserInfoUri, URI::create, 500, "카카오 유저 정보 요청 uri를 생성하지 못했습니다.");
        ValidateUtils.requireNotNull(kakaoOauthToken, 500, "kakaoOauthToken은 null일 수 없습니다.");
        String accessToken = kakaoOauthToken.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return RequestEntity.get(uri).headers(headers).build();
    }

    public void kakaoLogout() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        ValidateUtils.requireNotNull(auth, ApiErrorCode.NOT_AUTHENTICATED);
        Long userId = Long.valueOf((String) auth.getPrincipal());
        Login login = authHolder.get(userId);

        URI uri = ValidateUtils.requireApply(kakaoLogoutUri, URI::create, 500, "카카오 로그아웃 요청 uri를 생성하지 못했습니다.");
        ValidateUtils.requireNotNull(login, ApiErrorCode.ALREADY_LOGGED_OUT);
        String kakaoAccessToken = login.getKakaoAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        RequestEntity<?> request = RequestEntity.get(uri).headers(headers).build();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(request, Object.class);
    }
    public void googleLogout() {
        // TODO: 구글 로그아웃 구현
    }

    public void logout(Long userId) {
        ValidateUtils.requireNotNull(userId, 500, "userId는 null일 수 없습니다.");
        Login login = authHolder.get(userId);

        ValidateUtils.requireNotNull(login, ApiErrorCode.ALREADY_LOGGED_OUT);
        SocialPlatformType socialPlatformType = login.getSocialPlatformType();
        switch (socialPlatformType) {
            case KAKAO:
                kakaoLogout();
                break;
            case GOOGLE:
                googleLogout();
                break;
        }

        authHolder.remove(userId);
    }

    public Login switchRole(Long userId, UserRole userRole) {
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        Login login = authHolder.get(userId);
        if (!login.getUserRole().equals(userRole)) {
            login = switchLogin(user, login, userRole);
        }

        return login;
    }

    private Login switchLogin(User user, Login existsLogin, UserRole userRole) {
        ValidateUtils.requireNotNull(user, 500, "user는 null일 수 없습니다.");
        ValidateUtils.requireNotNull(existsLogin, 500, "existsLogin은 null일 수 없습니다.");
        ValidateUtils.requireNotNull(userRole, 500, "userRole은 null일 수 없습니다.");

        LoginRecord newloginRecord = LoginRecord.builder()
                .user(user)
                .roleName(userRole.name())
                .build();
        loginRecordRepository.save(newloginRecord);

        Set<String> authorities = Set.of(userRole.name());
        TokenHolder tokenHolder = JwtUtils.createTokens(user.getUserId().toString(), authorities, secretKey);
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

    public MentorDto joinMentor(@NotNull String company, @NotNull String introduction, Set<MentorCareerDto> careerDtoSet, Set<SkillStackType> skillStackTypeSet) {
        ValidateUtils.requireNotNull(company, 500, "company는 null일 수 없습니다.");
        ValidateUtils.requireNotNull(introduction, 500, "introduction은 null일 수 없습니다.");

        Long userId = AuthUtils.getUserId();
        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        Mentor newMentor = Mentor.from(user, company, introduction);
        Mentor savedMentor = mentorRepository.save(newMentor);

        if (skillStackTypeSet != null) {
            List<MentorSkillStack> mentorSkillStacks = skillStackTypeSet.stream()
                    .map(skillStackType -> {
                        SkillStack skillStack = skillStackRepository.findBySkillStackId(Long.valueOf(skillStackType.getSkillStackCode()))
                                .orElseThrow(ApiErrorCode.INTERNAL_SERVER_ERROR::exception);
                        MentorSkillStack mentorSkillStack = MentorSkillStack.builder()
                                .mentor(savedMentor)
                                .skillStack(skillStack)
                                .build();
                        return mentorSkillStackRepository.save(mentorSkillStack);
                    })
                    .collect(Collectors.toList());

            savedMentor.setMentorSkillStacks(mentorSkillStacks);
        }

        if (careerDtoSet != null) {
            careerDtoSet.forEach(careerDto->{
                MentorCareer newMentorCareer = MentorCareer.builder()
                        .mentor(savedMentor)
                        .duty(careerDto.getDutyType().name())
                        // TODO: period 타입변경 Integer -> String
//                        .period(careerDto.getPeriod())
                        .build();
                mentorCareerRepository.save(newMentorCareer);
            });
        }

        return MentorDto.fromEntity(savedMentor);
    }

    public TokenDto renewTokens(String refreshToken) {
        String userIdStr = ValidateUtils.requireApply(refreshToken, t->JwtUtils.getSubject(t, secretKey), ApiErrorCode.UNRELIABLE_JWT);
        Long userId = ValidateUtils.requireApply(userIdStr, Long::parseLong, ApiErrorCode.UNRELIABLE_JWT);
        Login login = ValidateUtils.requireNotNull(authHolder.get(userId), 404, "로그인 기록이 없습니다.");
        TokenHolder tokenHolder = JwtUtils.createTokens(userIdStr, Set.of(login.getUserRole().name()), secretKey);
        Login newLogin = Login.builder()
                .userRole(login.getUserRole())
                .accessToken(tokenHolder.getAccessToken())
                .refreshToken(tokenHolder.getRefreshToken())
                .socialAccessToken(login.getSocialAccessToken())
                .socialRefreshToken(login.getSocialRefreshToken())
                .socialPlatformType(login.getSocialPlatformType())
                .build();
        authHolder.put(userId, newLogin);
        return TokenDto.from(tokenHolder);
    }
}
