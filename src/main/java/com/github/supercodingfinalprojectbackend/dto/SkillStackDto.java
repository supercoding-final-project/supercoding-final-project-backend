package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.SkillStack;
import com.github.supercodingfinalprojectbackend.entity.type.SkillStackType;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SkillStackDto {
    private Long skillStackId;
    private String skillStackName;
    private String skillStackImg;

    public static SkillStackDto from(SkillStack skillStack) {
        return SkillStackDto.builder()
                .skillStackId(skillStack.getSkillStackId())
                .skillStackName(skillStack.getSkillStackName())
                .skillStackImg(skillStack.getSkillStackImg())
                .build();
    }

    public static SkillStackDto from(SkillStackType skillStackType) {
        return SkillStackDto.builder()
                .skillStackId(skillStackType.getSkillStackCode())
                .skillStackName(skillStackType.getSkillStackName())
                .build();
    }
}
