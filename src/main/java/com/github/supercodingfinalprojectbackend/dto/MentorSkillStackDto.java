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

    public static MentorSkillStackDto of(MentorDto mentorDto, MentorSkillStack mentorSkillStack) {
        return MentorSkillStackDto.builder()
                .mentorSkillStackId(mentorSkillStack.getMentorSkillStackId())
                .mentorDto(mentorDto)
                .skillStackDto(SkillStackDto.from(mentorSkillStack.getSkillStack()))
                .build();
    }
}
