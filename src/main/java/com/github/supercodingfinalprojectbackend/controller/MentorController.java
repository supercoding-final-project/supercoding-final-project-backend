package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MentorCareerDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto.MentorInfoResponse;
import com.github.supercodingfinalprojectbackend.dto.UserDto;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.service.MentorService;
import com.github.supercodingfinalprojectbackend.service.S3Service;
import com.github.supercodingfinalprojectbackend.service.UserService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/mentors")
@RestController
public class MentorController {

	private final MentorService mentorService;
	private final S3Service s3Service;

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

	@PostMapping(value = "/info", consumes = "multipart/form-data;charset=utf-8")
	@Operation(summary = "멘토 정보 수정")
	public void changeMentorInfo(
			@RequestParam(value = "thumbnailImageFile", required = false) @Parameter(name = "썸네일 이미지 파일") MultipartFile thumbnailImageFile,
			@RequestParam("nickname") @Parameter(name = "닉네임", required = true) String nickname,
			@RequestParam(value = "email", required = false) @Parameter(name = "이메일") String email,
			@RequestParam(value = "introduction", required = false) @Parameter(name = "소개글") String introduction,
			@RequestParam(value = "company", required = false) @Parameter(name = "현재 다니는 회사(현직)") String company,
			@RequestParam(value = "skillStack", required = false) @Parameter(name = "기술스택") List<String> skillStacks,
			@RequestParam(value = "career", required = false) @Parameter(name = "기술스택") List<MentorCareerDto.Request> careers,
			@RequestParam(value = "searchable", required = false) @Parameter(name = "검색 노출 유무") Boolean searchable
	) {
		CompletableFuture<Map<Integer, String>> future = CompletableFuture.supplyAsync(()-> {
			try {
				return s3Service.uploadImageFiles(thumbnailImageFile);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

//		future.thenApplyAsync();

		Long userId = AuthUtils.getUserId();


	}
}
