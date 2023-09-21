package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.PostDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.OrderCodeReviewDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.PostSearchDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.PostTimeResponseDto;
import com.github.supercodingfinalprojectbackend.service.PostService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
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
public class PostController {

    private final PostService postService;
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createPost(@RequestBody @Valid PostDto postDto) {
        Long userId = AuthUtils.getUserId();
        return postService.createPost(postDto,userId);
    }

    @GetMapping("/{postId}")
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
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long postId,@RequestBody @Valid PostDto postDto){
        Long userId = AuthUtils.getUserId();
        return postService.updatePost(userId,postId,postDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId){
        Long userId = AuthUtils.getUserId();
        return postService.deletePost(userId,postId);
    }

    @GetMapping("/day")
    public ResponseEntity<ApiResponse<PostTimeResponseDto>> getTimes(@RequestParam Long postId, @RequestParam String days){
        return postService.getTimes(postId,days);
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse<List<Integer>>> orderCodeReview(@RequestBody OrderCodeReviewDto orderCodeReviewDto) {
        Long userId = AuthUtils.getUserId();
        return postService.orderCodeReview(orderCodeReviewDto,userId);
    }

    @GetMapping("/mentor")
    public ResponseEntity<ApiResponse<PostSearchDto>> searchMentorPost(@RequestParam Long mentorId, @RequestParam Integer page, @RequestParam Integer size){
        return postService.searchMentorPost(mentorId,page-1,size);
    }

    @GetMapping("/search")
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
