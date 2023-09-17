package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.User;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    private Long userId;
    private UserAbstractAccountDto abstractAccount;
    private String email;
    private String nickname;
    private String thumbnailImageUrl;

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .abstractAccount(UserAbstractAccountDto.from(user.getAbstractAccount()))
                .email(user.getEmail())
                .nickname(user.getNickname())
                .thumbnailImageUrl(user.getThumbnailImageUrl())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class UserInfoResponse {
        private Long userId;
        private Long menteeId;
        private String email;
        private String nickname;
        private String thumbnailImageUrl;
        private MentorDto.MentorProfileResponse mentorProfile;

        public static UserInfoResponse of(Mentee mentee, Mentor mentor) {
            User user = mentee.getUser();
            return UserInfoResponse.builder()
                    .userId(user.getUserId())
                    .menteeId(mentee.getMenteeId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .thumbnailImageUrl(user.getThumbnailImageUrl())
                    .mentorProfile(mentor != null ? MentorDto.MentorProfileResponse.from(mentor) : null)
                    .build();
        }
    }
}
