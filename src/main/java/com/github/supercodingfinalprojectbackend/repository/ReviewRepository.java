package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.dto.ReviewSummary;
import com.github.supercodingfinalprojectbackend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(
            "SELECT r FROM Review r WHERE r.post.postId = :postId " +
                    "AND r.isDeleted = false " +
                    "AND r.reviewId > :cursor " +
                    "ORDER BY r.reviewId ASC "
    )
    Page<Review> findAllByPostIdAndIsDeletedWithCursor(Long postId, Long cursor, Pageable pageable);


    @Query(
            "SELECT r FROM Review r WHERE r.mentee.menteeId = :menteeId " +
                    "AND r.isDeleted = false " +
                    "AND r.reviewId > :cursor " +
                    "ORDER BY r.reviewId ASC "
    )
    Page<Review> findAllByMenteeIdAndIsDeletedWithCursor(Long menteeId, Long cursor, Pageable pageable);

    @Query(
            "SELECT new com.github.supercodingfinalprojectbackend.dto" +
                    ".ReviewSummary(COUNT(r.post.postId), SUM(r.star)) " +
                    "FROM Review r " +
                    "WHERE r.post.postId = :postId AND r.isDeleted = false "
    )
    ReviewSummary getReviewSummeryByPostId(Long postId);
}
