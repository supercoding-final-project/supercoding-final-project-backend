package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.User;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MenteeDto {
    private Long menteeId;
    private String accountNumber;
    private Long paymoney;
    private String email;
    private String name;
    private String nickname;
    private String thumbnailImageUrl;

    public static MenteeDto from(Mentee mentee) {
        User user = mentee.getUser();
        return MenteeDto.builder()
                .menteeId(mentee.getMenteeId())
                .accountNumber(user.getAbstractAccount().getAccountNumber())
                .paymoney(user.getAbstractAccount().getPaymoney())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .thumbnailImageUrl(user.getThumbnailImageUrl())
                .build();
    }
}
