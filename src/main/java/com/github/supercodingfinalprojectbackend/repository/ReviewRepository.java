package com.github.supercodingfinalprojectbackend.repository;

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
}
