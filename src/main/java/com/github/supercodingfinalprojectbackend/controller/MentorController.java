package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto.MentorInfoResponse;
import com.github.supercodingfinalprojectbackend.dto.response.ResponseUtils;
import com.github.supercodingfinalprojectbackend.dto.response.ResponseUtils.ApiResponse;
import com.github.supercodingfinalprojectbackend.service.MentorService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/mentors")
@RestController
public class MentorController {

	private final MentorService mentorService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<MentorInfoResponse>>> getMentors(
			@RequestParam String keyWord,
			@RequestParam List<String> skillStack,
			@RequestParam(defaultValue = "0") Long cursor,
			@RequestParam(defaultValue = "10") Integer pageSize
	){
			return ResponseUtils.ok(
					"Mentor 리스트를 성공적으로 가져왔습니다.",
					mentorService.getMentors(keyWord, skillStack).stream()
							.map(MentorDto.MentorInfoResponse::from)
							.collect(Collectors.toList())
			);
	}
}
