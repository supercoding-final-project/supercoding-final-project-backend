package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> {

    Optional<Mentee> findByUserAndIsDeletedIsFalse(User user);
    Optional<Mentee> findByUserUserIdAndIsDeletedIsFalse(Long userId);

    Mentee findByUserUserId(Long userId);
}
