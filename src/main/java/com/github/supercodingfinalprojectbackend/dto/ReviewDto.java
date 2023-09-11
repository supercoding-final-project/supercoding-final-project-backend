package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long reviewId;
    private Long menteeId;
    private Long postId;
    private String title;
    private String content;
    private Integer star;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getReviewId())
                .menteeId(review.getMentee().getMenteeId())
                .postId(review.getPost().getPostId())
                .title(review.getTitle())
                .content(review.getContent())
                .star(review.getStar())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateReviewRequest {

        private Long postId;
        private String title;
        private String content;
        private Integer star;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateReviewResponse {

        private Long reviewId;
        private Long menteeId;
        private Long postId;
        private String title;
        private String content;
        private Integer star;

        public static CreateReviewResponse from(ReviewDto reviewDto) {
            return CreateReviewResponse.builder()
                    .reviewId(reviewDto.getReviewId())
                    .menteeId(reviewDto.getMenteeId())
                    .postId(reviewDto.getPostId())
                    .title(reviewDto.getTitle())
                    .content(reviewDto.getContent())
                    .star(reviewDto.getStar())
                    .build();
        }

    }

}
