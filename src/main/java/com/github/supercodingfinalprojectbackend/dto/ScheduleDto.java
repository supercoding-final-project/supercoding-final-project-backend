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
    public Long mentorId;
    private List<Integer> sunday;
    private List<Integer> monday;
    private List<Integer> tuesday;
    private List<Integer> wednesday;
    private List<Integer> thursday;
    private List<Integer> friday;
    private List<Integer> saturday;
}
