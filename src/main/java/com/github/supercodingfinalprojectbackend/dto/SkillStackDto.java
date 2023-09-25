package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.SkillStack;
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Top10SkillStackResponse {
        private String skillStackName;
        private String skillStackImg;

        public static Top10SkillStackResponse from(SkillStackDto skillStackDto) {
            return Top10SkillStackResponse.builder()
                    .skillStackName(skillStackDto.getSkillStackName())
                    .skillStackImg(skillStackDto.getSkillStackImg())
                    .build();
        }
    }
}
