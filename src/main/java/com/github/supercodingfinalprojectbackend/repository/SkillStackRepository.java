package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.SkillStack;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillStackRepository extends JpaRepository<SkillStack, Long> {
    Optional<SkillStack> findBySkillStackId(Long skillStackCode);
    Optional<SkillStack> findBySkillStackName(String skillName);

    @Query("SELECT s FROM SkillStack s ORDER BY s.skillStackSearchCount DESC")
    List<SkillStack> findTop10ByOrderBySkillStackSearchCountDesc(Pageable pageable);
}
