package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

        @NotNull(message = "postId 값을 입력해 주세요.")
        private Long postId;
        @NotNull(message = "title 값을 입력해 주세요.")
        private String title;
        @NotNull(message = "content 값을 입력해 주세요.")
        private String content;
        @Min(value = 1, message = "평점의 최소값은 1 이상이여야 합니다.")
        @Max(value = 5, message = "평점의 최대값은 5 이하이여야 합니다.")
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
