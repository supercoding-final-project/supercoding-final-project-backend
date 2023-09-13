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

    public static MentorCareerDto from(Request request) {
        try {
            return MentorCareerDto.builder()
                    .dutyType(DutyType.valueOf(request.dutyName.toUpperCase()).resolve())
                    .period(request.period)
                    .build();
        } catch (IllegalArgumentException e) {
            throw ApiErrorCode.INVALID_DUTY.exception();
        }
    }

    public static MentorCareerDto from(MentorCareer mentorCareer) {
        return MentorCareerDto.builder()
                .dutyType(DutyType.valueOf(mentorCareer.getDuty()))
                .period(mentorCareer.getPeriod())
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
}
