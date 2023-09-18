package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.ReviewDto;
import com.github.supercodingfinalprojectbackend.dto.ReviewSummary;
import com.github.supercodingfinalprojectbackend.entity.*;
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

import java.util.List;
import java.util.Objects;

import static com.github.supercodingfinalprojectbackend.dto.ReviewDto.*;
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

    @Transactional
    public ReviewDto createReview(CreateReviewRequest request, Long userId) {

        Long inputPostId = request.getPostId();
        String inputContent = request.getContent();
        Integer inputStar = request.getStar();

        Mentee mentee = menteeRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_MENTEE));

        if (!mentee.isValid()) {
            throw new ApiException(DELETED_MENTEE);
        }

        Posts post = postsRepository.findById(inputPostId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_POST));

        Mentor mentor = post.getMentor();

        /**
         * 자격, 작성 시점 : 거래가 완료(환불 X), 예약된 시간 1시간 이후, 일주일 이내
         */
//        List<OrderSheet> menteeOrderHistory = orderSheetRepository.findAllByPostAndMenteeAndAndIsCompletedTrue(post, mentee);
//        if (menteeOrderHistory.isEmpty()) {
//            throw new ApiException(UNABLE_TO_WRITE_REVIEW);
//        }

        /**
         * 포스트, 멘토 평점 Update
         */
        updateStar(mentor, post, inputStar);


        return from(
                reviewRepository.save(
                        toEntity(mentee, post, inputContent, inputStar))
        );
    }

    public Page<ReviewDto> getReviews(Long postId, Long cursor, Pageable pageable) {

        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_POST));

        Page<Review> reviews = reviewRepository
                .findAllByPostIdAndIsDeletedWithCursor(posts.getPostId(), cursor, pageable);

        return reviews.map(ReviewDto::from);
    }

    public Page<ReviewDto> getMyReviews(Long userId, Long cursor, Pageable pageable) {

        Mentee mentee = menteeRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_MENTEE));

        if (!mentee.isValid()) {
            throw new ApiException(DELETED_MENTEE);
        }

        Page<Review> reviews = reviewRepository
                .findAllByMenteeIdAndIsDeletedWithCursor(mentee.getMenteeId(), cursor, pageable);

        return reviews.map(ReviewDto::from);
    }

    @Transactional
    public ReviewDto deleteReview(Long userId, Long reviewId) {

        Mentee mentee = menteeRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_MENTEE));

        if (!mentee.isValid()) {
            throw new ApiException(DELETED_MENTEE);
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ApiException(NOT_FOUND_REVIEW));

        if (!review.isValid(mentee.getMenteeId())) {
            throw new ApiException(UNABLE_TO_DELETE_REVIEW);
        }

        review.delete();
        return from(review);
    }

    public void updateStar(Mentor mentor, Posts post, Integer inputStar) {

        log.info("post.getPostId() : {}", post.getPostId());
        ReviewSummary reviewSummery = reviewRepository.getReviewSummeryByPostId(post.getPostId());

        float totalStar = reviewSummery.getTotalStar() + inputStar;
        float reviewCount = reviewSummery.getReviewCount() + 1;
        float updatePostStar  = totalStar / reviewCount;
        post.updateStar(updatePostStar);

        List<Posts> mentorPosts = postsRepository.findAllByMentorAndIsDeletedFalse(mentor);
        float sumMentorPostsStar = 0.0f;
        float postCount = mentorPosts.size();
        for (Posts mentorPost : mentorPosts) {
            if (Objects.equals(mentorPost.getPostId(), post.getPostId())) {
                sumMentorPostsStar += updatePostStar;
            } else {
                sumMentorPostsStar += mentorPost.getStar();
            }
        }
        float updateMentorStar = sumMentorPostsStar/postCount;
        mentor.updateStar(updateMentorStar);
    }
}
