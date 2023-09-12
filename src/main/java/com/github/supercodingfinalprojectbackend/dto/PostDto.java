package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Posts;
import com.github.supercodingfinalprojectbackend.entity.PostsContent;
import com.github.supercodingfinalprojectbackend.entity.type.PostContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class PostDto {
    @NotBlank
    private String title;
    @NotBlank
    private String level;
    @NotNull
    private Integer price;
    @NotBlank
    private String post_stack;
    @NotNull
    @Size(min = 1)
    private List<String> work_career;
    @NotNull
    @Size(min = 1)
    private List<String> educate_career;
    @NotNull
    @Size(min = 1)
    private List<String> review_style;

    public static PostDto PostInfoResponse(Posts posts, List<PostsContent> postsContent, String skillStack) {

        List<String> workCareerList = postContentToList(postsContent,PostContentType.WORK_CAREER);
        List<String> educateCareerList = postContentToList(postsContent,PostContentType.EDUCATE_CAREER);
        List<String> reviewStyleList = postContentToList(postsContent,PostContentType.REVIEW_STYLE);

        return PostDto.builder()
                .title(posts.getTitle())
                .level(posts.getLevel())
                .price(posts.getPrice())
                .post_stack(skillStack)
                .work_career(workCareerList)
                .educate_career(educateCareerList)
                .review_style(reviewStyleList)
                .build();
    }

    public static List<String> postContentToList(List<PostsContent> postsContents, PostContentType type){
        return postsContents.stream()
                .map(PostsContent::getContentType)
                .filter(contentType -> contentType.equals(type.name()))
                .collect(Collectors.toList());
    }

}
