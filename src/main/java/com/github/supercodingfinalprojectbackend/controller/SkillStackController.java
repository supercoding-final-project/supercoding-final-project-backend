package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.SkillStackDto.Top10SkillStackResponse;
import com.github.supercodingfinalprojectbackend.service.SkillStackService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/skillStacks")
@RestController
public class SkillStackController {

    private final SkillStackService skillStackService;

    @GetMapping("/top10")
    public ResponseEntity<ApiResponse<List<Top10SkillStackResponse>>> getReviews(
    ) {
            return ResponseUtils.ok(
                    "SkillStack Top 10을 조회에 성공하였습니다.",
                    skillStackService.getTop10SkillStacks().stream()
                            .map(Top10SkillStackResponse::from)
                            .collect(Collectors.toList())
            );
    }
}
