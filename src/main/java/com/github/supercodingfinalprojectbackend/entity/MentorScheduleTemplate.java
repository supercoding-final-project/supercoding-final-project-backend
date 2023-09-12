package com.github.supercodingfinalprojectbackend.entity;


import com.github.supercodingfinalprojectbackend.dto.ScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "mentor_schedule_templates")
public class MentorScheduleTemplate {

    @Id
    @Column(name = "schedule_template_id",nullable = false) @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleTemplateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @Column(name = "schedule_week")
    private String scheduleWeek;

    @Column(name = "valid_time")
    private Integer validTime;

    public static MentorScheduleTemplate from(ScheduleDto scheduleDto, Mentor mentor, Integer validTime) {
        return MentorScheduleTemplate.builder()
                .mentor(mentor)
                .scheduleWeek((scheduleDto.getScheduleWeek())) // 날짜를 설정
                .validTime(validTime) // validTime을 MentorScheduleTemplate에 추가
                .build();
    }
}
