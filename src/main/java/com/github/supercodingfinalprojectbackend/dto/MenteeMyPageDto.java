package com.github.supercodingfinalprojectbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenteeMyPageDto {
    private Long userId;
    private String nickname;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class  ResponseChangeInfo{
        private String nickname;
    }
}
