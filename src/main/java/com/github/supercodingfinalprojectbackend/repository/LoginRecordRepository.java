package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.LoginRecord;
import com.github.supercodingfinalprojectbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecord, Long> {

    Optional<LoginRecord> findFirstByUserAndIsDeletedIsFalseOrderByCreatedAtDesc(User user);
}
