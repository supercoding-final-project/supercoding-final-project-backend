package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Posts;
import com.github.supercodingfinalprojectbackend.entity.PostsContent;
import com.github.supercodingfinalprojectbackend.entity.type.PostContentType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDto {
    private Long postId;
    private MentorDto mentorDto;
    @NotBlank
    private String title;
    @NotBlank
    private String level;
    @NotNull
    private Integer price;
    @NotBlank
    private String postStack;
    @NotNull
    @Size(min = 1)
    private List<String> workCareer;
    @NotNull
    @Size(min = 1)
    private List<String> educateCareer;
    @NotNull
    @Size(min = 1)
    private List<String> reviewStyle;

    public static PostDto PostInfoResponse(Posts posts, List<PostsContent> postsContent, String skillStack) {

        List<String> workCareerList = postContentToList(postsContent,PostContentType.WORK_CAREER);
        List<String> educateCareerList = postContentToList(postsContent,PostContentType.EDUCATE_CAREER);
        List<String> reviewStyleList = postContentToList(postsContent,PostContentType.REVIEW_STYLE);

        return PostDto.builder()
                .title(posts.getTitle())
                .level(posts.getLevel())
                .price(posts.getPrice())
                .postStack(skillStack)
                .workCareer(workCareerList)
                .educateCareer(educateCareerList)
                .reviewStyle(reviewStyleList)
                .build();
    }

    public static List<String> postContentToList(List<PostsContent> postsContents, PostContentType type){
        return postsContents.stream()
                .map(PostsContent::getContentType)
                .filter(contentType -> contentType.equals(type.name()))
                .collect(Collectors.toList());
    }

    public static PostDto from(Posts post) {
        return PostDto.builder()
                .postId(post.getPostId())
                .mentorDto(MentorDto.from(post.getMentor()))
                .title(post.getTitle())
                .level(post.getLevel())
                .price(post.getPrice())
                .build();
    }
}
