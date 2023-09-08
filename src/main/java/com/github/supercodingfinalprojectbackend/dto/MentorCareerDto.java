package com.github.supercodingfinalprojectbackend.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MentorCareerDto {
    private String duty;
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

    public String getFullString() { return duty + " " + period; }
}
