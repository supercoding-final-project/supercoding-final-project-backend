package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.ReviewDto;
import com.github.supercodingfinalprojectbackend.service.ReviewService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.supercodingfinalprojectbackend.dto.ReviewDto.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/api/reviews")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ResponseUtils.ApiResponse<CreateReviewResponse>> createReview(
            @RequestBody CreateReviewRequest request
    ){
            Long userId = AuthUtils.getUserId();
            return ResponseUtils.created(
                    "리뷰 작성을 성공하였습니다.",
                    ReviewDto.CreateReviewResponse.from(
                            reviewService.createReview(request, userId)
                    )
            );
    }
}
