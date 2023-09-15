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
import static com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode.*;

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

        Mentee mentee = menteeRepository.findByUserUserIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_MENTEE));

        Posts posts = postsRepository.findById(inputPostId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_POST));

        // TODO: 결제한 사람만 가능 or 리뷰가 끝난 시점?, 횟수만큼 작성가능?



        return ReviewDto.from(
                reviewRepository.save(
                        toEntity(mentee, posts, inputTitle, inputContent, inputStar))
        );
    }

    public Page<ReviewDto> getReviews(Long postId, Long cursor, Pageable pageable) {

        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_POST));

        Page<Review> reviews = reviewRepository
                .findAllByPostIdAndIsDeletedWithCursor(posts.getPostId(), cursor, pageable);

        return reviews.map(ReviewDto :: from);
    }

    public Page<ReviewDto> getMyReviews(Long userId, Long cursor, Pageable pageable) {

        Mentee mentee = menteeRepository.findByUserUserId(userId);
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
