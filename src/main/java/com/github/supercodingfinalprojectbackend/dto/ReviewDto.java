package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long reviewId;
    private Long postId;
    private String title;
    private Long mentorId;
    private String mentorNickname;
    private Long menteeId;
    private String menteeNickname;
    private String content;
    private Integer star;
    private Integer numberOfReviewsReceived;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getReviewId())
                .postId(review.getPost().getPostId())
                .title(review.getPost().getTitle())
                .mentorId(review.getPost().getMentor().getMentorId())
                .mentorNickname(review.getPost().getMentor().getUser().getNickname())
                .menteeId(review.getMentee().getMenteeId())
                .menteeNickname(review.getMentee().getUser().getNickname())
                .content(review.getContent())
                .star(review.getStar())
                .numberOfReviewsReceived(review.getNumberOfReviewsReceived())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateReviewRequest {

        @NotNull(message = "orderSheetId 값을 입력해 주세요.")
        private Long orderSheetId;

        @NotNull(message = "content 값을 입력해 주세요.")
        private String content;

        @NotNull
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
        private String content;
        private Integer star;
        private Integer numberOfReviewsReceived;

        public static CreateReviewResponse from(ReviewDto reviewDto) {
            return CreateReviewResponse.builder()
                    .reviewId(reviewDto.getReviewId())
                    .menteeId(reviewDto.getMenteeId())
                    .postId(reviewDto.getPostId())
                    .content(reviewDto.getContent())
                    .star(reviewDto.getStar())
                    .numberOfReviewsReceived(reviewDto.getNumberOfReviewsReceived())
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
        private String content;
        private Integer star;
        private Integer numberOfReviewsReceived;

        public static ReviewResponse from(ReviewDto reviewDto) {
            return ReviewResponse.builder()
                    .reviewId(reviewDto.getReviewId())
                    .postId(reviewDto.getPostId())
                    .menteeId(reviewDto.getMenteeId())
                    .nickname(reviewDto.getMenteeNickname())
                    .content(reviewDto.getContent())
                    .star(reviewDto.getStar())
                    .numberOfReviewsReceived(reviewDto.getNumberOfReviewsReceived())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MyReviewResponse {

        private Long reviewId;
        private Long postId;
        private String title;
        private Long mentorId;
        private String mentorNickname;
        private String content;
        private Integer star;

        public static MyReviewResponse from(ReviewDto reviewDto) {
            return MyReviewResponse.builder()
                    .reviewId(reviewDto.getReviewId())
                    .postId(reviewDto.getPostId())
                    .title(reviewDto.getTitle())
                    .mentorId(reviewDto.getMentorId())
                    .mentorNickname(reviewDto.getMentorNickname())
                    .content(reviewDto.getContent())
                    .star(reviewDto.getStar())
                    .build();
        }
    }
}
