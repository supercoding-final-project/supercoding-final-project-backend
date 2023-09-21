package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.PostDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.OrderCodeReviewDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.PostSearchDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.PostTimeResponseDto;
import com.github.supercodingfinalprojectbackend.service.PostService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@RestController
@Tag(name = "포스트 API")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "포스트 등록")
    public ResponseEntity<ApiResponse<Long>> createPost(@RequestBody @Valid PostDto postDto) {
        Long userId = AuthUtils.getUserId();
        return postService.createPost(postDto,userId);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "특정 포스트 조회")
    public ResponseEntity<ApiResponse<PostDto>> getPost(@PathVariable Long postId){
        long userId;
        try{
            userId = AuthUtils.getUserId();
        }catch (Exception e){
            userId = 0L;
        }
        return postService.getPost(postId,userId);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "포스트 수정")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long postId,@RequestBody @Valid PostDto postDto){
        Long userId = AuthUtils.getUserId();
        return postService.updatePost(userId,postId,postDto);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "포스트 삭제")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId){
        Long userId = AuthUtils.getUserId();
        return postService.deletePost(userId,postId);
    }

    @GetMapping("/day")
    @Operation(summary = "코드리뷰 신청시 특정 날짜 조회")
    public ResponseEntity<ApiResponse<PostTimeResponseDto>> getTimes(@RequestParam Long postId, @RequestParam String days){
        return postService.getTimes(postId,days);
    }

    @PostMapping("/order")
    @Operation(summary = "코드리뷰 신청")
    public ResponseEntity<ApiResponse<List<Integer>>> orderCodeReview(@RequestBody OrderCodeReviewDto orderCodeReviewDto) {
        Long userId = AuthUtils.getUserId();
        return postService.orderCodeReview(orderCodeReviewDto,userId);
    }

    @GetMapping("/mentor")
    @Operation(summary = "특정 멘토의 포스트 검색")
    public ResponseEntity<ApiResponse<PostSearchDto>> searchMentorPost(@RequestParam Long mentorId, @RequestParam Integer page, @RequestParam Integer size){
        return postService.searchMentorPost(mentorId,page-1,size);
    }

    @GetMapping("/search")
    @Operation(summary = "포스트 검색")
    public ResponseEntity<ApiResponse<PostSearchDto>> searchPost(
            @RequestParam String word,
            @RequestParam(required = false) List<String> stackCategory,
            @RequestParam(required = false) List<String> skillStack,
            @RequestParam(required = false) List<String> level,
            @RequestParam Integer page,
            @RequestParam Integer size){
        return postService.searchPost(word, stackCategory,skillStack, level, page-1,size);
    }
}
