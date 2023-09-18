package com.github.supercodingfinalprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSummary {
    private Long reviewCount;
    private Long totalStar;
}
