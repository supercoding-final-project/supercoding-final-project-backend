package com.github.supercodingfinalprojectbackend.controller;


import com.github.supercodingfinalprojectbackend.dto.ScheduleDto;
import com.github.supercodingfinalprojectbackend.service.MentorTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentor/schedules")
public class MentorTemplateController {
    private final MentorTemplateService mentorTemplateService;

    @PostMapping()
    @Operation(summary = "멘토 스케쥴 설정")
    public ResponseEntity<?> postSchedules(@RequestBody ScheduleDto scheduleDto){
        return mentorTemplateService.scheduleMentor(scheduleDto);
    }
}
