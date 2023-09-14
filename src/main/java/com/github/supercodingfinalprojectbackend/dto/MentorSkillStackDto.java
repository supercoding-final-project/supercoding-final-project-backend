package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.MentorSkillStack;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MentorSkillStackDto {
    private Long mentorSkillStackId;
    private MentorDto mentorDto;
    private SkillStackDto skillStackDto;

    public static MentorSkillStackDto from(MentorSkillStack mentorSkillStack) {
        return MentorSkillStackDto.builder()
                .mentorSkillStackId(mentorSkillStack.getMentorSkillStackId())
                .mentorDto(MentorDto.from(mentorSkillStack.getMentor()))
                .skillStackDto(SkillStackDto.from(mentorSkillStack.getSkillStack()))
                .build();
    }

    public static MentorSkillStackDto from(SkillStackType skillStackType) {
        return MentorSkillStackDto.builder()
                .skillStackDto(SkillStackDto.from(skillStackType))
                .build();
    }

    public void setMentorDto(MentorDto mentorDto) {
        this.mentorDto = mentorDto;
    }
}
