package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.ReviewDto;
import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.Posts;
import com.github.supercodingfinalprojectbackend.entity.Review;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.repository.MenteeRepository;
import com.github.supercodingfinalprojectbackend.repository.OrderSheetRepository;
import com.github.supercodingfinalprojectbackend.repository.PostsRepository;
import com.github.supercodingfinalprojectbackend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.supercodingfinalprojectbackend.entity.Review.toEntity;
import static com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode.NOT_FOUND_REVIEW;
import static com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode.UNABLE_TO_DELETE_REVIEW;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final MenteeRepository menteeRepository;
    private final PostsRepository postsRepository;
    private final ReviewRepository reviewRepository;
    private final OrderSheetRepository orderSheetRepository;

    public ReviewDto createReview(ReviewDto.CreateReviewRequest request, Long userId) {

        Long inputPostId = request.getPostId();
        String inputTitle = request.getTitle();
        String inputContent = request.getContent();
        Integer inputStar = request.getStar();

        Mentee mentee = menteeRepository.findByUserUserId(userId);

        // TODO: 예외처리 생기면 refactoring,
        //  postsRepository Long 타입으로 바뀌면 수정
        Posts posts = postsRepository.findById(inputPostId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 포스트 입니다."));

        // TODO: 결제한 사람만 가능 or 리뷰가 끝난 시점?, 횟수만큼 작성가능?

        // TODO: 별점 최소 1 최대 5 유효성 검사 해야함 (DTO 에서)


        return ReviewDto.from(
                reviewRepository.save(
                        toEntity(mentee, posts, inputTitle, inputContent, inputStar))
        );
    }

    public Page<ReviewDto> getReviews(Long postId, Long cursor, Pageable pageable) {

        // TODO: 예외처리 생기면 refactoring,
        //  postsRepository Long 타입으로 바뀌면 수정
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 포스트 입니다."));

        Page<Review> reviews = reviewRepository
                .findAllByPostIdAndIsDeletedWithCursor(posts.getPostId(), cursor, pageable);

        return reviews.map(ReviewDto :: from);
    }

    public Page<ReviewDto> getMyReviews(Long userId, Long cursor, Pageable pageable) {

        Mentee mentee = menteeRepository.findByUserUserId(userId);
        log.info("menteeId : {}", mentee.getMenteeId());
        Page<Review> reviews = reviewRepository
                .findAllByMenteeIdAndIsDeletedWithCursor(mentee.getMenteeId(), cursor, pageable);

        return reviews.map(ReviewDto :: from);
    }

    @Transactional
    public ReviewDto deleteReview(Long userId, Long reviewId) {

        Mentee mentee = menteeRepository.findByUserUserId(userId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_REVIEW));

        if (!review.isValid(mentee.getMenteeId())) {
            throw new ApiException(UNABLE_TO_DELETE_REVIEW);
        }

        review.delete();
        return ReviewDto.from(review);
    }
}
