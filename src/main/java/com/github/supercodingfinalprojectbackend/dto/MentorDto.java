package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.MentorCareer;
import com.github.supercodingfinalprojectbackend.entity.MentorSkillStack;
import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.entity.type.DutyType;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
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
				.mentorCareerList(mentor.getMentorCareerList().stream()
						.map(MentorCareerDto::from)
						.collect(Collectors.toList()))
				.mentorSkillStackList(mentor.getMentorSkillStacks()
						.stream().map(MentorSkillStackDto::from)
						.collect(Collectors.toList()))
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
	@Schema(name = "멘토 등록 요청 객체")
	public static class JoinRequest {
		@Schema(name = "현재 다니는 회사")
		private String company;
		@Schema(name = "멘토 소개글")
		private String introduction;
		@Schema(name = "커리어")
		private List<MentorCareerDto.Request> careers;
		@Schema(name = "기술스택")
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
		private List<MentorSkillStackDto> mentorSkillStackList;
		private List<MentorCareerDto> mentorCareerList;

		public static MentorDetailResponse from(MentorDto mentorDto) {
			return MentorDetailResponse.builder()
					.mentorId(mentorDto.getMentorId())
					.nickname(mentorDto.getNickname())
					.introduction(mentorDto.getIntroduction())
					.company(mentorDto.getCompany())
					.thumbnailImageUrl(mentorDto.getThumbnailImageUrl())
					.mentorSkillStackList(mentorDto.getMentorSkillStackList())
					.mentorCareerList(mentorDto.getMentorCareerList())
					.build();
		}

	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@ToString
	@Schema(name = "멘토 정보 수정 요청 객체")
	public static class ChangeInfoRequest {
		@Schema(name = "썸네일 이미지 URL")
		private String thumbnailImageUrl;
		@Schema(name = "닉네임")
		private String nickname;
		@Schema(name = "이메일")
		private String email;
		@Schema(name = "멘토 소개글")
		private String introduction;
		@Schema(name = "현재 다니는 회사")
		private String company;
		@Schema(name = "기술 스택들")
		private List<String> skillStacks;
		@Schema(name = "커리어들")
		private List<MentorCareerDto.Request> careers;
		@Schema(name = "검색 노출 유무")
		private Boolean searchable;

		public boolean validate() {
			if (careers != null && !careers.isEmpty() && !careers.stream().allMatch(MentorCareerDto.Request::validate)) return false;
			if (skillStacks != null && !skillStacks.isEmpty() && !skillStacks.stream().allMatch(SkillStackType::contains)) return false;

			return nickname != null &&
					searchable != null;
		}

		public String getCurrentDuty() {
			return careers != null && !careers.isEmpty() ? careers.get(0).getDutyName() : null;
		}

		public String getCurrentPeriod() {
			return careers != null && !careers.isEmpty() ? careers.get(0).getPeriod() : null;
		}
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@ToString
	public static class ChangeInfoResponse {
		@Schema(name = "썸네일 이미지 URL")
		private String thumbnailImageUrl;
		@Schema(name = "닉네임")
		private String nickname;
		@Schema(name = "이메일")
		private String email;
		@Schema(name = "멘토 소개글")
		private String introduction;
		@Schema(name = "현재 다니는 회사")
		private String company;
		@Schema(name = "기술 스택들")
		private List<String> skillStacks;
		@Schema(name = "커리어들")
		private List<MentorCareerDto.Response> careers;
		@Schema(name = "검색 노출 유무")
		private Boolean searchable;

		public static ChangeInfoResponse from(Mentor mentor) {
			User user = mentor.getUser();

			List<MentorCareer> mentorCareers = mentor.getMentorCareerList();
			List<MentorCareerDto.Response> careers = mentorCareers != null && !mentorCareers.isEmpty() ?
					mentorCareers.stream()
							.map(MentorCareerDto.Response::from)
							.collect(Collectors.toList())
					: null;

			List<MentorSkillStack> mentorSkillStacks = mentor.getMentorSkillStacks();
			List<String> skillStacks = mentorSkillStacks != null && !mentorSkillStacks.isEmpty() ?
					mentorSkillStacks.stream()
							.map(MentorSkillStack::getSkillStackName)
							.collect(Collectors.toList())
					: null;

			return ChangeInfoResponse.builder()
					.thumbnailImageUrl(user.getThumbnailImageUrl())
					.nickname(user.getNickname())
					.email(user.getEmail())
					.introduction(mentor.getIntroduction())
					.company(mentor.getCompany())
					.careers(careers)
					.skillStacks(skillStacks)
					.searchable(mentor.getSearchable())
					.build();
		}
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class InfoResponse {
		private String nickname;
		private String email;
		private String thumbnailImageUrl;
		private String introduction;
		private String company;
		private Boolean searchable;
		private String currentDuty;
		private String currentPeriod;
		private List<MentorCareerDto.Response> careers;
		private List<String> skillStacks;

		public static InfoResponse of(Mentor mentor, List<MentorCareer> mentorCareers, List<MentorSkillStack> mentorSkillStacks) {
			User user = mentor.getUser();

			return InfoResponse.builder()
					.nickname(user.getNickname())
					.email(user.getEmail())
					.thumbnailImageUrl(user.getThumbnailImageUrl())
					.introduction(mentor.getIntroduction())
					.company(mentor.getCompany())
					.searchable(mentor.getSearchable())
					.currentDuty(DutyType.resolvedName(mentor.getCurrentDuty()))
					.currentPeriod(mentor.getCurrentPeriod())
					.careers(mentorCareers.stream().map(MentorCareerDto.Response::from).collect(Collectors.toList()))
					.skillStacks(mentorSkillStacks.stream().map(MentorSkillStack::getSkillStackName).collect(Collectors.toList()))
					.build();
		}
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@ToString
	public static class MentorProfileResponse {
		private Long mentorId;
		private String company;
		private String introduction;
		private String currentDutyName;
		private String currentPeriod;
		private Boolean searchable;
		private List<MentorCareerDto.Response> careers;
		private List<String> skillStacks;

		public static MentorProfileResponse from(Mentor mentor) {
			List<MentorCareerDto.Response> careers = mentor.getMentorCareerList() != null ? mentor.getMentorCareerList().stream().map(MentorCareerDto.Response::from).collect(Collectors.toList()) : null;
			List<String> skillStacks = mentor.getMentorSkillStacks() != null ? mentor.getMentorSkillStacks().stream().map(MentorSkillStack::getSkillStackName).collect(Collectors.toList()) : null;

			return MentorProfileResponse.builder()
					.mentorId(mentor.getMentorId())
					.company(mentor.getCompany())
					.introduction(mentor.getIntroduction())
					.currentDutyName(DutyType.resolvedName(mentor.getCurrentDuty()))
					.currentPeriod(mentor.getCurrentPeriod())
					.searchable(mentor.getSearchable())
					.careers(careers)
					.skillStacks(skillStacks)
					.build();
		}
	}
}
