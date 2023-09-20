package com.github.supercodingfinalprojectbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "skill_stacks")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillStack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_stack_id", nullable = false)
	private Long skillStackId;

	@Column(name = "skill_stack_name")
	private String skillStackName;

	@Column(name = "skill_stack_img")
	private String skillStackImg;

	@Column(name = "skill_stack_search_count")
	private Long skillStackSearchCount;
}
