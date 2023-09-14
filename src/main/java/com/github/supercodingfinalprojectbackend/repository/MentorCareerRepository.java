package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.MentorCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorCareerRepository extends JpaRepository<MentorCareer, Long> {

    List<MentorCareer> findAllByMentorAndIsDeletedIsFalse(Mentor mentor);
}
