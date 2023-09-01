package com.github.supercodingfinalprojectbackend.entity;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "mentor_schedule_templates")
public class MentorScheduleTemplate {

    @Id  @Column(name = "schedule_template_id",nullable = false) @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleTemplateId;

    @Column(name = "mentor_id")
    private Long mentorId;

    @Column(name = "schedule_week")
    private String scheduleWeek;

    @Column(name = "valid_time")
    private Integer validTime;

}
