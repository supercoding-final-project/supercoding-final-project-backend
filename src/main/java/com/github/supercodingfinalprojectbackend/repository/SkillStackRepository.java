package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.SkillStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillStackRepository extends JpaRepository<SkillStack, Long> {

    Optional<SkillStack> findBySkillStackId(Long skillStackCode);
}
