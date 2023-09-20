package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long>, MentorRepositoryCustom {

//    Optional<Mentor> findByUserAndIsDeletedIsFalse(User user);

    Optional<Mentor> findByUserUserIdAndIsDeletedIsFalse(Long userId);

    Optional<Mentor> findByMentorIdAndIsDeletedIsFalse(Long userId);

    boolean existsByUserUserIdAndIsDeletedIsFalse(Long userId);

    List<Mentor> findAllByMentorIdInAndIsDeletedIsFalseOrderByMentorIdAsc(Collection<Long> mentorId);
}
