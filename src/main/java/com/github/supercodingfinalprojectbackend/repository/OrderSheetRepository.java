package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {

    Optional<List<OrderSheet>> findByMenteeAndIsDeletedIsFalse(Mentee MenteeId);

    Optional<List<OrderSheet>> findByMenteeAndIsDeletedIsFalseAndIsCompletedIsTrue(Mentee MenteeId);
    Optional<OrderSheet> findByPostMentorAndOrderSheetIdAndIsDeletedIsFalse(Mentor mentor, Long orderSheetId);
}
