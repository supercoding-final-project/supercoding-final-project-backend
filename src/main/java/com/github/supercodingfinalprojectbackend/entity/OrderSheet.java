package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "order_sheets")
public class OrderSheet extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_sheet_id")
    private int id;
    @Column(name = "mentee_id")
    private int menteeId;
    @Column(name = "mentee_nickname")
    private String menteeNickname;
    @Column(name = "mentee_account_number")
    private String menteeAccountNumber;
    @Column(name = "mentor_id")
    private int mentorId;
    @Column(name = "mentor_nickname")
    private String mentorNickname;
    @Column(name = "mentor_account_number")
    private String mentorAccountNumber;
    @Column(name = "post_id")
    private int postId;
    @Column(name = "post_title")
    private String postTitle;
    @Column(name = "post_level")
    private String postLevel;
    @Column(name = "price_per_hour")
    private int pricePerHour;
    @Column(name = "is_completed")
    private boolean isCompleted;
}
