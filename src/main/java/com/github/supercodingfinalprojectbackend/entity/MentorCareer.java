package com.github.supercodingfinalprojectbackend.entity;

import com.github.supercodingfinalprojectbackend.dto.MentorCareerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "mentor_careers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorCareer extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mentor_career_id", nullable = false)
	private Long mentorCareerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mentor_id")
	private Mentor mentor;

	@Column(name = "duty")
	private String duty;

	@Column(name = "period")
	private String period;

	public static MentorCareer create(Mentor mentor, MentorCareerDto mentorCareerDto) {
		return MentorCareer.builder()
				.mentor(mentor)
				.duty(mentorCareerDto.getDutyType().resolve().name())
				.period(mentorCareerDto.getPeriod())
				.build();
	}

	public static MentorCareer of(Mentor mentor, MentorCareerDto.Request career) {
		return MentorCareer.builder()
				.mentor(mentor)
				.duty(career.getDutyName())
				.period(career.getPeriod())
				.build();
	}

	public void softDelete() {
		this.isDeleted = true;
	}
}
