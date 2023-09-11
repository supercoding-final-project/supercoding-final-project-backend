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
    private UserDto user;

    public static MenteeDto from(Mentee mentee) {
        User user = mentee.getUser();
        return MenteeDto.builder()
                .menteeId(mentee.getMenteeId())
                .user(UserDto.from(mentee.getUser()))
                .build();
    }
}
