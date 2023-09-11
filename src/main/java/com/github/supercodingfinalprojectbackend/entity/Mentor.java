package com.github.supercodingfinalprojectbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "mentor")
	private List<MentorSkillStack> mentorSkillStacks = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "introduction")
	private String introduction;

	@Column(name = "company")
	private String company;

	@Column(name = "searchable")
	private Boolean searchable;

	@Column(name = "current_duty")
	private String currentDuty;

	@Column(name = "current_period")
	private String currentPeriod;

	public static Mentor from(User user, String company, String introduction) {
		return Mentor.builder()
				.mentorSkillStacks(null)
				.user(user)
				.introduction(introduction)
				.searchable(false)
				.company(company)
				.build();
	}

	public void setMentorSkillStacks(List<MentorSkillStack> mentorSkillStacks) { this.mentorSkillStacks = mentorSkillStacks; }
}
