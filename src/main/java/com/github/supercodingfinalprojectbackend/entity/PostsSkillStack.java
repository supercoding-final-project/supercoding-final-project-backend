package com.github.supercodingfinalprojectbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts_skill_stack")
public class PostsSkillStack extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stack_id", nullable = false)
    private Long stackId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_stack_id")
    private SkillStack skillStack;

    public static PostsSkillStack fromPost(Posts posts,SkillStack skillStack){
        return PostsSkillStack.builder()
                .posts(posts)
                .skillStack(skillStack)
                .build();
    }

    public void skillStackUpdate(SkillStack skillStack){
        this.skillStack  = skillStack;
    }
}
