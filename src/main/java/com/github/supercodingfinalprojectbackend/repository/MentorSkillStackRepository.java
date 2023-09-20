package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.MentorSkillStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorSkillStackRepository extends JpaRepository<MentorSkillStack, Long> {

    List<MentorSkillStack> findAllByMentorAndIsDeletedIsFalse(Mentor mentor);

    void deleteAllByMentor(Mentor mentor);
}
