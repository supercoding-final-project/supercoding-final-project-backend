package com.github.supercodingfinalprojectbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "mentor_skill_stacks")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorSkillStack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mentor_skill_stack_id", nullable = false)
	private Long mentorSkillStackId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mentor_id")
	private Mentor mentor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skill_stack_id")
	private SkillStack skillStack;
}
