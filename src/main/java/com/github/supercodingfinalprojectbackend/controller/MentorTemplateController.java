package com.github.supercodingfinalprojectbackend.controller;


import com.github.supercodingfinalprojectbackend.dto.ScheduleDto;
import com.github.supercodingfinalprojectbackend.service.MentorTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentor/schedules")
@Tag(name = "멘토 스케줄 API")
public class MentorTemplateController {
    private final MentorTemplateService mentorTemplateService;

    @PostMapping()
    @Operation(summary = "멘토 스케줄 설정")
    public ResponseEntity<?> postSchedules(@RequestBody ScheduleDto scheduleDto) throws IllegalAccessException {
        return mentorTemplateService.scheduleMentor(scheduleDto);
    }
}
