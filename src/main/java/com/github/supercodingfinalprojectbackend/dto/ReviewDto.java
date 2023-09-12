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
    private Long postId;
    private Long menteeId;
    private String nickname;
    private String title;
    private String content;
    private Integer star;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getReviewId())
                .postId(review.getPost().getPostId())
                .menteeId(review.getMentee().getMenteeId())
                .nickname(review.getMentee().getUser().getNickname())
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReviewResponse {

        private Long reviewId;
        private Long postId;
        private Long menteeId;
        private String nickname;
        private String title;
        private String content;
        private Integer star;

        public static ReviewResponse from(ReviewDto reviewDto) {
            return ReviewResponse.builder()
                    .reviewId(reviewDto.getReviewId())
                    .postId(reviewDto.getPostId())
                    .menteeId(reviewDto.getMenteeId())
                    .nickname(reviewDto.getNickname())
                    .title(reviewDto.getTitle())
                    .content(reviewDto.getContent())
                    .star(reviewDto.getStar())
                    .build();
        }
    }

}
