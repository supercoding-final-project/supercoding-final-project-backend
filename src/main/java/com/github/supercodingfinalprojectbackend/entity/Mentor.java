package com.github.supercodingfinalprojectbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "mentors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mentor extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mentor_id", nullable = false)
	private Long mentorId;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "name")
	private String name;

	@Column(name = "introduction")
	private String introduction;

	@Column(name = "company")
	private String company;

	@Column(name = "searchable")
	private Boolean searchable;
}
