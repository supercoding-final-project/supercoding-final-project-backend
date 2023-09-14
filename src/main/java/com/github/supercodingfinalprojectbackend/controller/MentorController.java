package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto.MentorInfoResponse;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.service.MentorService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentors")
@RestController
@Tag(name = "멘토 API")
public class MentorController {

	private final MentorService mentorService;

	@GetMapping
	public ResponseEntity<ApiResponse<Page<MentorInfoResponse>>> getMentors(
			@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false) List<String> skillStack,
			@RequestParam(defaultValue = "0") Long cursor,
			@RequestParam(defaultValue = "10") Integer pageSize
	){
			return ResponseUtils.ok(
					"Mentor 리스트를 성공적으로 가져왔습니다.",
					mentorService.getMentors(keyword, skillStack, cursor, PageRequest.of(0, pageSize))
			);
	}

	@GetMapping("/detail/{mentorId}")
	public ResponseEntity<ApiResponse<MentorDto.MentorDetailResponse>> getMentorDetail(
			@PathVariable("mentorId") Long mentorId
	){
			return ResponseUtils.ok(
					"Mentor 상세정보를 성공적으로 가져왔습니다.",
					MentorDto.MentorDetailResponse.from(
							mentorService.getMentorDetail(mentorId))
			);
	}

	@PostMapping(value = "/info")
	@Operation(summary = "멘토 정보 수정")
	public ResponseEntity<ApiResponse<MentorDto.ChangeInfoResponse>> changeMentorInfo(@RequestBody MentorDto.ChangeInfoRequest request) {
		ValidateUtils.requireTrue(request.validate(), ApiErrorCode.INVALID_REQUEST_BODY);
		Long userId = AuthUtils.getUserId();
		MentorDto.ChangeInfoResponse response = mentorService.changeMentorInfo(userId, request);
		return ResponseUtils.ok("멘토의 정보가 성공적으로 수정되었습니다.", response);
	}
}
