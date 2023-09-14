package com.github.supercodingfinalprojectbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private Long mentorId;
    private String scheduleWeek;
    private List<Integer> validTime;
}
