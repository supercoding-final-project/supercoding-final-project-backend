package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.MentorCareer;
import com.github.supercodingfinalprojectbackend.entity.type.DutyType;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import lombok.*;

import java.util.function.Predicate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MentorCareerDto {
    private DutyType dutyType;
    private String period;
    private String fullString;

    @Override
    public int hashCode() {
        return dutyType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MentorCareerDto)) return false;
        MentorCareerDto other = (MentorCareerDto) obj;
        return dutyType.equals(other.getDutyType());
    }

    public static MentorCareerDto from(MentorCareer mentorCareer) {
        return MentorCareerDto.builder()
                .dutyType(DutyType.valueOf(mentorCareer.getDuty()))
                .period(mentorCareer.getPeriod())
                .fullString(DutyType.valueOf(mentorCareer.getDuty()) + " " + mentorCareer.getPeriod())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @EqualsAndHashCode(of = {"dutyName"})
    public static class Request {
        private String dutyName;
        private String period;

        public boolean validate() {
            try {
                DutyType.valueOf(dutyName);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @EqualsAndHashCode(of = {"dutyName"})
    public static class Response {
        private String dutyName;
        private String period;

        public static Response from(MentorCareer mentorCareer) {
            return Response.builder()
                    .dutyName(DutyType.resolvedName(mentorCareer.getDuty()))
                    .period(mentorCareer.getPeriod())
                    .build();
        }
    }
}
