package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "selectedId", callSuper = false)
@Table(name = "selected_class_times")
public class SelectedClassTime extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selected_class_time_id")
    private Long selectedId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "day")
    private Integer day;

    @Column(name = "hour")
    private Integer hour;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;
    @ManyToOne
    @JoinColumn(name = "order_sheet_id")
    private OrderSheet orderSheet;

    public void beRejected() {
        this.isDeleted = true;
    }

    public static SelectedClassTime of(LocalDate date, Integer time, Mentor mentor, Mentee mentee, OrderSheet orderSheet){

        return SelectedClassTime.builder()
                .year(date.getYear())
                .month(date.getMonthValue())
                .day(date.getDayOfMonth())
                .hour(time)
                .mentor(mentor)
                .mentee(mentee)
                .orderSheet(orderSheet)
                .build();
    }
}
