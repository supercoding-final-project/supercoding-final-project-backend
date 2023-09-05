package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "loginRecordId", callSuper = false)
@Table(name = "login_records")
public class LoginRecord extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_record_id")
    private Long loginRecordId;
    @ManyToOne
    @Column(name = "user_id")
    private User user;
    @Column(name = "role_name")
    private String roleName;
}
