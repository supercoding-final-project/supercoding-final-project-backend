package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.PostDto;
import com.github.supercodingfinalprojectbackend.dto.PostDto.OrderCodeReviewDto;
import com.github.supercodingfinalprojectbackend.service.PostService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils.ApiResponse;
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
    public ResponseEntity<ApiResponse<Void>> createPost(@RequestBody @Valid PostDto postDto) {
//        Long userId = AuthUtils.getUserId();
        Long userId = 1L;
        return postService.createPost(postDto,userId);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDto>> getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long postId,@RequestBody @Valid PostDto postDto){
        return postService.updatePost(postId,postDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }

    @GetMapping("/day")
    public ResponseEntity<ApiResponse<List<Integer>>> getTimes(@RequestParam Long postId, @RequestParam String days){
        return postService.getTimes(postId,days);
    }

    @PostMapping("/order")
    public ResponseEntity<ApiResponse<List<Integer>>> orderCodeReview(@RequestBody OrderCodeReviewDto orderCodeReviewDto) {
        Long userId = 1L;
        return postService.orderCodeReview(orderCodeReviewDto,userId);
    }
}
