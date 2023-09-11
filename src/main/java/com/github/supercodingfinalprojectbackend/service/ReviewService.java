package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.dto.ReviewDto;
import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.Posts;
import com.github.supercodingfinalprojectbackend.repository.MenteeRepository;
import com.github.supercodingfinalprojectbackend.repository.OrderSheetRepository;
import com.github.supercodingfinalprojectbackend.repository.PostsRepository;
import com.github.supercodingfinalprojectbackend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.github.supercodingfinalprojectbackend.entity.Review.toEntity;

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
        Posts posts = postsRepository.findById(Math.toIntExact(inputPostId))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 포스트 입니다."));

        // TODO: 결제한 사람만 가능 or 리뷰가 끝난 시점?, 횟수만큼 작성가능?

        // TODO: 별점 최소 1 최대 5 유효성 검사 해야함 (DTO 에서)


        return ReviewDto.from(
                reviewRepository.save(
                        toEntity(mentee, posts, inputTitle, inputContent, inputStar))
        );
    }
}
