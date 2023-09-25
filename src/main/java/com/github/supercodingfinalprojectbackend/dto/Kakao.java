package com.github.supercodingfinalprojectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

public class Kakao {
    @Getter
    @ToString
    public static class Account {
        @JsonProperty("profile_needs_agreement")
        private Boolean profileNeedsAgreement;
        @JsonProperty("profile_nickname_needs_agreement")
        private Boolean profileNicknameNeedsAgreement;
        @JsonProperty("profile_image_needs_agreement")
        private Boolean profileImageNeedsAgreement;
        @JsonProperty("profile")
        private Kakao.Profile profile;
        @JsonProperty("name_needs_agreement")
        private Boolean nameNeedsAgreement;
        @JsonProperty("name")
        private String name;
        @JsonProperty("email_needs_agreement")
        private Boolean emailNeedsAgreement;
        @JsonProperty("is_email_valid")
        private Boolean isEmailValid;
        @JsonProperty("is_email_verified")
        private Boolean isEmailVerified;
        @JsonProperty("email")
        private String email;
        @JsonProperty("age_range_needs_agreement")
        private Boolean ageRangeNeedsAgreement;
        @JsonProperty("age_range")
        private String ageRange;
        @JsonProperty("birthyear_needs_agreement")
        private Boolean birthyearNeedsAgreement;
        @JsonProperty("birthyear")
        private String birthyear;
        @JsonProperty("birthday_needs_agreement")
        private Boolean birthdayNeedsAgreement;
        @JsonProperty("birthday")
        private String birthday;
        @JsonProperty("birthday_type")
        private String birthdayType;
        @JsonProperty("gender_needs_agreement")
        private Boolean genderNeedsAgreement;
        @JsonProperty("gender")
        private String gender;
        @JsonProperty("phone_number_needs_agreement")
        private Boolean phoneNumberNeedsAgreement;
        @JsonProperty("phone_number")
        private String phoneNumber;
        @JsonProperty("ci_needs_agreement")
        private Boolean ciNeedsAgreement;
        @JsonProperty("ci")
        private String ci;
        @JsonProperty("ci_authenticated_at")
        private Instant ciAuthenticatedAt;
    }

    @Getter
    @ToString
    public static class OauthToken {
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("id_token")
        private String idToken;
        @JsonProperty("expires_in")
        private Integer expiresIn;
        @JsonProperty("refresh_token")
        private String refreshToken;
        @JsonProperty("refresh_token_expires_in")
        private Integer refreshTokenExpiresIn;
        @JsonProperty("scope")
        private String scope;
    }

    @Getter
    @ToString
    public static class Partner {
        @JsonProperty("uuid")
        private String uuid;
    }

    @Getter
    @ToString
    public static class Profile {
        @JsonProperty("nickname")
        private String nickname;
        @JsonProperty("thumbnail_image_url")
        private String thumbnailImageUrl;
        @JsonProperty("profile_image_url")
        private String profileImageUrl;
        @JsonProperty("is_default_image")
        private Boolean isDefaultImage;
    }

    @Getter
    @ToString
    public static class Property {
    }

    @Getter
    @ToString
    public static class UserInfo {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("has_signed_up")
        private Boolean hasSignedUp;
        @JsonProperty("connected_at")
        private Instant connetedAt;
        @JsonProperty("synched_at")
        private Instant synchedAt;
        @JsonProperty("properties")
        private Kakao.Property property;
        @JsonProperty("kakao_account")
        private Kakao.Account kakaoAccount;
        @JsonProperty("for_partner")
        private Kakao.Partner partner;
    }
}
