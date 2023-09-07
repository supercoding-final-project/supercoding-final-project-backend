package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MentorDto.MentorInfoResponse;
import com.github.supercodingfinalprojectbackend.service.MentorService;
<<<<<<< HEAD
import java.util.List;
import java.util.stream.Collectors;

import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
=======
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
>>>>>>> 849728ceb1b2855cc99dbc6eebb262bc7cf3bf24
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/mentors")
@RestController
public class MentorController {

	private final MentorService mentorService;

	@GetMapping
<<<<<<< HEAD
	public ResponseEntity<ResponseUtils.ApiResponse<List<MentorInfoResponse>>> getMentors(
			@RequestParam String keyWord,
			@RequestParam List<String> skillStack,
=======
	public ResponseEntity<ApiResponse<Page<MentorInfoResponse>>> getMentors(
			@RequestParam(required = false, defaultValue = "") String keyWord,
			@RequestParam(required = false) List<String> skillStack,
>>>>>>> 849728ceb1b2855cc99dbc6eebb262bc7cf3bf24
			@RequestParam(defaultValue = "0") Long cursor,
			@RequestParam(defaultValue = "10") Integer pageSize
	){
//			return ResponseUtils.ok(
//					"Mentor 리스트를 성공적으로 가져왔습니다.",
//					mentorService.getMentors(keyWord, skillStack).stream()
//							.map(MentorDto.MentorInfoResponse::from)
//							.collect(Collectors.toList())
//			);

			return ResponseUtils.ok(
					"Mentor 리스트를 성공적으로 가져왔습니다.",
					mentorService.getMentors(keyWord, skillStack, cursor, PageRequest.of(0, pageSize))
			);
	}
}
