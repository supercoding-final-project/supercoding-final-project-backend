package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.dto.Reviewable;
import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import com.github.supercodingfinalprojectbackend.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {

    List<OrderSheet> findAllByMenteeUserUserIdAndIsCompletedIsFalse(Long userId);

    List<OrderSheet> findAllByMenteeUserUserIdAndIsCompletedIsTrue(Long userId);


    List<OrderSheet> findAllByPostMentorUserUserIdAndIsCompletedTrue(Long userId);


    List<OrderSheet> findAllByPostMentorUserUserIdAndIsCompletedIsFalse(Long userId);

    Optional<OrderSheet> findByPostMentorAndOrderSheetIdAndIsDeletedIsFalseAndIsCompletedIsFalse(Mentor mentor, Long orderSheetId);

    Optional<OrderSheet> findByPostMentorUserUserIdAndOrderSheetIdAndIsDeletedIsFalseAndIsCompletedIsFalse(Long userId, Long orderSheetId);

    List<OrderSheet> findAllByMenteeUserUserIdAndOrderSheetIdIsInAndIsCompletedIsFalseAndIsDeletedIsFalse(Long userId, Set<Long> orderSheetIdSet);

    List<OrderSheet> findAllByPostAndMenteeAndIsCompletedTrue(Posts post, Mentee mentee);
    @Query(
            "SELECT new com.github.supercodingfinalprojectbackend.dto" +
            ".Reviewable(os.orderSheetId, m.mentorId, u.nickname, p.postId, p.title, SUM(sct.hour), os.totalPrice) FROM OrderSheet os " +
            "LEFT JOIN SelectedClassTime sct ON os.orderSheetId = sct.orderSheet.orderSheetId " +
            "LEFT JOIN Posts p ON os.post.postId = p.postId " +
            "LEFT JOIN Mentor m ON p.mentor.mentorId = m.mentorId " +
            "LEFT JOIN User u ON m.user.userId = u.userId " +
            "WHERE os.isCompleted = true " +
            "AND os.mentee.menteeId = :menteeId " +
            "AND os.is_reviewed = false " +
            "AND os.orderSheetId > :cursor " +
            "AND os.orderSheetId IN (" +
            "   SELECT DISTINCT osSub.orderSheetId " +
            "   FROM OrderSheet osSub " +
            "   LEFT JOIN SelectedClassTime sctSub ON osSub.orderSheetId = sctSub.orderSheet.orderSheetId " +
            "   WHERE :currentTime BETWEEN sctSub.reservationTime " +
            "   AND sctSub.reviewAvailableTime " +
            ") " +
            "GROUP BY os.orderSheetId"
    )
    Page<Reviewable> findReviewableOrderSheetByMenteeId(Long menteeId, LocalDateTime currentTime, Long cursor, Pageable pageable);
}
