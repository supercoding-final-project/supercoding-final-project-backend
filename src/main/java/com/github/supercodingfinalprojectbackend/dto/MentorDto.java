package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.type.DutyType;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;
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
	private Set<MentorCareerDto> mentorCareerSet;
	private Set<SkillStackType> skillStackTypeSet;
	private List<MentorCareerDto> mentorCareerList;

	public static MentorDto from(Mentor mentor){
		return MentorDto.builder()
				.mentorId(mentor.getMentorId())
				.nickname(mentor.getUser().getNickname())
				.email(mentor.getUser().getEmail())
				.thumbnailImageUrl(mentor.getUser().getThumbnailImageUrl())
				.introduction(mentor.getIntroduction())
				.company(mentor.getCompany())
				.currentDuty(DutyType.valueOf(mentor.getCurrentDuty()).resolve())
				.currentPeriod(mentor.getCurrentPeriod())
				.mentorCareerSet(mentor.getMentorCareerList().stream().map(MentorCareerDto::from).collect(Collectors.toSet()))
				.mentorCareerList(mentor.getMentorCareerList().stream().map(MentorCareerDto::from).collect(Collectors.toList()))
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
				.mentorCareerSet(careers)
				.skillStackTypeSet(skillStackTypes)
				.build();
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
