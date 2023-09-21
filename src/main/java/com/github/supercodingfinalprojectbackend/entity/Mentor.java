package com.github.supercodingfinalprojectbackend.entity;

import com.github.supercodingfinalprojectbackend.dto.MentorCareerDto;
import com.github.supercodingfinalprojectbackend.dto.MentorDto;
import com.github.supercodingfinalprojectbackend.util.DummyUtils;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "mentors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "mentorId", callSuper = false)
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

	@OneToMany(mappedBy = "mentor")
	private List<MentorCareer> mentorCareerList = new ArrayList<>();

	@Column(name = "star")
	private Float star;

	public static Mentor of(User user, String company, String introduction) {
		return Mentor.builder()
				.user(user)
				.company(company)
				.introduction(introduction)
				.searchable(false)
				.star(0.0f)
				.build();
	}

	public static Mentor dummy(User dummyUser) {
		return Mentor.builder()
				.user(dummyUser)
				.company("코디밸롭")
				.introduction("안녕하세요. 천상천하유아독존 " + dummyUser.getNickname() + "입니다!")
				.searchable(randomSearchable())
				.star(randomStar())
				.build();
	}

	private static Float randomStar() {
		float min = 0.0f;
		float max = 5.0f;
		float value = min + (max - min) * new Random().nextFloat();
		return Math.round(value * 10) / 10.0f;
	}

	private static Boolean randomSearchable() {
		return new Random().nextBoolean();
	}

	public void setMentorSkillStacks(List<MentorSkillStack> mentorSkillStacks) { this.mentorSkillStacks = mentorSkillStacks; }

	@Override
	public boolean isValid() {
		return !this.getIsDeleted() && this.getSearchable();
	}

	public void changeInfo(String introduction, String company, List<MentorSkillStack> mentorSkillStacks, List<MentorCareer> mentorCareers, Boolean searchable) {
		if (introduction != null) this.introduction = introduction;
		if (company != null) this.company = company;
		if (mentorSkillStacks != null) this.mentorSkillStacks = mentorSkillStacks;
		if (mentorCareers != null) this.mentorCareerList = mentorCareers;
		if (searchable != null) this.searchable = searchable;
	}

	public void setMentorCareers(List<MentorCareer> mentorCareers) {
		if (mentorCareers != null && !mentorCareers.isEmpty()) {
			this.currentDuty = mentorCareers.get(0).getDuty();
			this.currentPeriod = mentorCareers.get(0).getPeriod();
		}
		this.mentorCareerList = mentorCareers;
	}

	public void changeInfo(MentorDto.ChangeInfoRequest request) {
		this.user.changeInfo(request.getNickname(), request.getEmail(), request.getThumbnailImageUrl());
		this.introduction = request.getIntroduction();
		this.company = request.getCompany();
		this.searchable = request.getSearchable();
		this.currentDuty = request.getCurrentDuty();
		this.currentPeriod = request.getCurrentPeriod();
	}

	public void updateStar(Float star) {
		this.star = star;
	}
}
