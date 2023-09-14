package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.MentorScheduleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MentorScheduleTemplateRepository extends JpaRepository<MentorScheduleTemplate, Long> {
        List<MentorScheduleTemplate> findByMentorAndScheduleWeek(Mentor mentor, String week);

}
