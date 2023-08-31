package com.github.supercodingfinalprojectbackend.entity;

import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Mentor_Skill_Stacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorSkillStack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mentor_skill_stack_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mentor_id")
	private Mentor mentor;

	@Column(name = "skill_stack")
	private String skillStack;
}
