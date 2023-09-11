package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorDto {

	private Long mentorId;
	private Long mentorAbstractAccountId;
	private String name;
	private String nickname;
	private String email;
	private String thumbnailImageUrl;
	private String introduction;
	private String company;
	private Boolean searchable;

	public static MentorDto fromEntity(Mentor mentor){
		return MentorDto.builder()
				.mentorId(mentor.getMentorId())
				.name(mentor.getUser().getName())
				.nickname(mentor.getUser().getNickname())
				.introduction(mentor.getIntroduction())
				.email(mentor.getUser().getEmail())
				.company(mentor.getCompany())
				.thumbnailImageUrl(mentor.getUser().getThumbnailImageUrl())
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

		public static MentorInfoResponse from(MentorDto mentorDto){
			return MentorInfoResponse.builder()
					.mentorId(mentorDto.getMentorId())
					.introduction(mentorDto.getIntroduction())
					.company(mentorDto.getCompany())
					.thumbnailImageUrl(mentorDto.getThumbnailImageUrl())
					.build();
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
		private Set<MentorCareerDto.Request> careers;
		private Set<String> skillStackNames;
	}
}
