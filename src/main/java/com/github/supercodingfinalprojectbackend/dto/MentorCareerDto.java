package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.MentorCareer;
import com.github.supercodingfinalprojectbackend.entity.type.DutyType;
import lombok.*;

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
        return getFullString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MentorCareerDto)) return false;
        MentorCareerDto other = (MentorCareerDto) obj;
        return getFullString().equals(other.getFullString());
    }

    public String getFullString() { return dutyType.name() + " " + period; }

    public static MentorCareerDto from(Request request) throws IllegalArgumentException {
        return MentorCareerDto.builder()
                .dutyType(DutyType.valueOf(request.dutyName.toUpperCase()).resolve())
                .period(request.period)
                .build();
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
    @EqualsAndHashCode(of = {"dutyName", "period"})
    public static class Request {
        private String dutyName;
        private String period;
    }
}
