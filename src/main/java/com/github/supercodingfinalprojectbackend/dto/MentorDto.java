package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.MentorSkillStack;
import com.github.supercodingfinalprojectbackend.entity.type.DutyType;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorDto {

	private Long mentorId;
	private Long mentorAbstractAccountId;
	private String nickname;
	private String email;
	private String thumbnailImageUrl;
	private String introduction;
	private String company;
	private Boolean searchable;
	private DutyType currentDuty;
	private String currentPeriod;
	private List<MentorSkillStackDto> mentorSkillStackList;
	private List<MentorCareerDto> mentorCareerList;

	public static MentorDto from(Mentor mentor){
		return MentorDto.builder()
				.mentorId(mentor.getMentorId())
				.mentorAbstractAccountId(mentor.getUser().getAbstractAccount().getAbstractAccountId())
				.nickname(mentor.getUser().getNickname())
				.email(mentor.getUser().getEmail())
				.thumbnailImageUrl(mentor.getUser().getThumbnailImageUrl())
				.introduction(mentor.getIntroduction())
				.company(mentor.getCompany())
				.currentDuty(DutyType.valueOf(mentor.getCurrentDuty()).resolve())
				.currentPeriod(mentor.getCurrentPeriod())
				.mentorCareerList(mentor.getMentorCareerList().stream().map(MentorCareerDto::from).collect(Collectors.toList()))
				.mentorSkillStackList(mentor.getMentorSkillStacks().stream().map(MentorSkillStackDto::from).collect(Collectors.toList()))
				.build();
	}

	public static MentorDto from(JoinRequest request) {
		Objects.requireNonNull(request);

		String company = ValidateUtils.requireNotNullElse(request.company, "");
		String introduction = ValidateUtils.requireNotNullElse(request.introduction, "");
		List<MentorCareerDto.Request> careers = ValidateUtils.requireNotNullElse(request.careers, List.of());


		DutyType currentDuty = ValidateUtils.requireApply(request.careers.get(0).getDutyName(), s->DutyType.valueOf(s).resolve(), ApiErrorCode.INVALID_DUTY);

		List<MentorCareerDto> mentorCareerDtoList = request.careers.stream()
				.map(MentorCareerDto::from)
				.collect(Collectors.toList());

		List<MentorSkillStackDto> mentorSkillStackDtoList = request.skillStackNames.stream()
				.map(skillStackName->ValidateUtils.requireApply(skillStackName, SkillStackType::valueOf, ApiErrorCode.INVALID_SKILL_STACK))
				.map(MentorSkillStackDto::from)
				.collect(Collectors.toList());

		MentorDto mentorDto = MentorDto.builder()
				.company(company)
				.introduction(introduction)
				.currentDuty(currentDuty)
				.currentPeriod(request.careers.get(0).getPeriod())
				.mentorCareerList(mentorCareerDtoList)
				.mentorSkillStackList(mentorSkillStackDtoList)
				.build();

		mentorSkillStackDtoList.forEach(s->s.setMentorDto(mentorDto));

		return mentorDto;
	}

	@Getter
	@NoArgsConstructor
	@Builder
	public static class MentorInfoResponse {

		private Long mentorId;
		private String nickname;
		private String thumbnailImageUrl;
		private String introduction;
		private String company;
		private String currentDuty;
		private String currentPeriod;

		@QueryProjection
		public MentorInfoResponse(
				Long mentorId, String nickname, String introduction, String company,
				String currentDuty, String currentPeriod, String thumbnailImageUrl
		) {
			this.mentorId = mentorId;
			this.nickname = nickname;
			this.introduction = introduction;
			this.company = company;
			this.currentDuty = currentDuty;
			this.currentPeriod = currentPeriod;
			this.thumbnailImageUrl = thumbnailImageUrl;
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
		private List<MentorCareerDto.Request> careers;
		private List<String> skillStackNames;

		public boolean validate() {
			return company != null && introduction != null;
		}
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

		public static JoinResponse from(Mentor mentor) {
			return new JoinResponse(mentor.getMentorId());
		}
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class MentorDetailResponse {
		private Long mentorId;
		private String nickname;
		private String introduction;
		private String company;
		private String thumbnailImageUrl;
		private List<MentorCareerDto> mentorCareerList;

		public static MentorDetailResponse from(MentorDto mentorDto) {
			return MentorDetailResponse.builder()
					.mentorId(mentorDto.getMentorId())
					.nickname(mentorDto.getNickname())
					.introduction(mentorDto.getIntroduction())
					.company(mentorDto.getCompany())
					.thumbnailImageUrl(mentorDto.getThumbnailImageUrl())
					.mentorCareerList(mentorDto.getMentorCareerList())
					.build();
		}

	}
}
