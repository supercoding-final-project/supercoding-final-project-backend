package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Mentor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private String introduction;
	private String company;
	private Boolean searchable;

	public static MentorDto fromEntity(Mentor mentor){
		return MentorDto.builder()
				.mentorId(mentor.getMentorId())
//				Test 때 mentor 계좌가 없이 테스트 하느라 임시 주석
//				.mentorAbstractAccountId(
//						mentor.getMentorAbstractAccount().getMentorAbstractAccountId())
				.name(mentor.getName())
				.nickname(mentor.getNickname())
				.introduction(mentor.getIntroduction())
				.email(mentor.getEmail())
				.company(mentor.getCompany())
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
		private String introduction;
		private String company;

		public static MentorInfoResponse from(MentorDto mentorDto){
			return MentorInfoResponse.builder()
					.mentorId(mentorDto.getMentorId())
					.name(mentorDto.getName())
					.nickname(mentorDto.getNickname())
					.email(mentorDto.getEmail())
					.introduction(mentorDto.getIntroduction())
					.company(mentorDto.getCompany())
					.build();
		}
	}

}
