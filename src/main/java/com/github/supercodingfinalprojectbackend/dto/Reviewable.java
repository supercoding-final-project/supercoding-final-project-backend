package com.github.supercodingfinalprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reviewable {

    private Long oderSheetId;
    private Long mentorId;
    private String mentorNickname;
    private Long postId;
    private String title;
    private Long totalTime;
    private Integer totalPrice;
}
