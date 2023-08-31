package com.github.supercodingfinalprojectbackend.entity;

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
@Table(name = "Mentor_Careers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorCareer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mentor_career_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mentor_id")
	private Mentor mentor;

	@Column(name = "company")
	private String company;

	@Column(name = "duty")
	private String duty;

	@Column(name = "period")
	private Integer period;
}
