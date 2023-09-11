package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import com.querydsl.core.types.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {

    Optional<OrderSheet> findByPostMentorAndOrderSheetIdAndIsDeletedIsFalse(Mentor mentor, Long orderSheetId);
}
