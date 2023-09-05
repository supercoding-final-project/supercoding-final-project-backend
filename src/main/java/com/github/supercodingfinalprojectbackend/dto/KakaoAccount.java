package com.github.supercodingfinalprojectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public class KakaoAccount {
    @JsonProperty("profile_needs_agreement")
    private Boolean profileNeedsAgreement;
    @JsonProperty("profile_nickname_needs_agreement")
    private Boolean profileNicknameNeedsAgreement;
    @JsonProperty("profile_image_needs_agreement")
    private Boolean profileImageNeedsAgreement;
    @JsonProperty("profile")
    private KakaoProfile profile;
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
