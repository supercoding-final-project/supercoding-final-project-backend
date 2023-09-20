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

    private Long skillStackId;
    private String skillStackName;
    private String skillStackImg;

    public static MentorSkillStackDto from(MentorSkillStack mentorSkillStack) {
        return MentorSkillStackDto.builder()
                .skillStackId(mentorSkillStack.getSkillStack().getSkillStackId())
                .skillStackName(mentorSkillStack.getSkillStack().getSkillStackName())
                .skillStackImg(mentorSkillStack.getSkillStack().getSkillStackImg())
                .build();
    }
}
