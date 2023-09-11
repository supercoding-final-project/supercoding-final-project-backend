package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.type.DutyType;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorDto {

	private Long mentorId;
	private Long mentorAbstractAccountId;
	private String name;    // TODO: name 제거
	private String nickname;
	private String email;
	private String thumbnailImageUrl;
	private String introduction;
	private String company;
	private Boolean searchable;
	private DutyType currentDuty;
	private String currentPeriod;
	private Set<MentorCareerDto> careerDtoSet;
	private Set<SkillStackType> skillStackTypeSet;

	public static MentorDto from(Mentor mentor){
		return MentorDto.builder()
				.mentorId(mentor.getMentorId())
//				.name(mentor.getUser().getName())
				.nickname(mentor.getUser().getNickname())
				.email(mentor.getUser().getEmail())
				.thumbnailImageUrl(mentor.getUser().getThumbnailImageUrl())
				.introduction(mentor.getIntroduction())
				.company(mentor.getCompany())
				.currentDuty(DutyType.valueOf(mentor.getCurrentDuty()).resolve())
				.currentPeriod(mentor.getCurrentPeriod())
				.build();
	}

	public static MentorDto from(JoinRequest request) {
		DutyType currentDuty = ValidateUtils.requireApply(request.currentDuty, s->DutyType.valueOf(s).resolve(), 400, "존재하지 않는 직무입니다.");

		Set<MentorCareerDto> careers = request.careers.stream()
				.map(MentorCareerDto::from)
				.collect(Collectors.toSet());

		Set<SkillStackType> skillStackTypes = request.skillStackNames.stream()
				.map(skillStackName->ValidateUtils.requireApply(skillStackName, SkillStackType::valueOf, 400, "존재하지 않는 기술스택입니다."))
				.collect(Collectors.toSet());

		return MentorDto.builder()
				.company(request.company)
				.introduction(request.introduction)
				.currentDuty(currentDuty)
				.currentPeriod(request.currentPeriod)
				.careerDtoSet(careers)
				.skillStackTypeSet(skillStackTypes)
				.build();
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class MentorInfoResponse {

		private Long mentorId;
		private String name;
		private String nickname;
		private String email;
		private String thumbnailImageUrl;
		private String introduction;
		private String company;

		@QueryProjection
		public MentorInfoResponse(Long mentorId, String name, String introduction, String company){
			this.mentorId = mentorId;
			this.name = name;
			this.introduction = introduction;
			this.company = company;
		}
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@ToString
	public static class JoinRequest {
		private String company;
		private String introduction;
		private String currentDuty;
		private String currentPeriod;
		private Set<MentorCareerDto.Request> careers;
		private Set<String> skillStackNames;
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@ToString
	public static class JoinResponse {
		private Long mentorId;

		public static JoinResponse from(MentorDto mentorDto) {
			return new JoinResponse(mentorDto.getMentorId());
		}
	}
}
