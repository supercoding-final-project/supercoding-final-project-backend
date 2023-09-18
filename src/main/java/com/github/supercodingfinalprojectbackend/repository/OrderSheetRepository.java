package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import com.github.supercodingfinalprojectbackend.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
