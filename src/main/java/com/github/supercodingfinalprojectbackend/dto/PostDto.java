package com.github.supercodingfinalprojectbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long postId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MentorDto mentorDto;
    @NotBlank
    private String title;
    @NotBlank
    private String level;
    @NotNull
    private Integer price;
    @NotBlank
    private String postStack;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean permission;
    @NotNull
    @Size(min = 1)
    private List<String> workCareer;
    @NotNull
    @Size(min = 1)
    private List<String> educateCareer;
    @NotNull
    @Size(min = 1)
    private List<String> reviewStyle;

    public static PostDto PostInfoResponse(Posts posts, List<PostsContent> postsContent, String skillStack, boolean permission) {

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
                .permission(permission)
                .build();
    }

    public static List<String> postContentToList(List<PostsContent> postsContents, PostContentType type){
        return postsContents.stream()
                .filter(contentType -> contentType.getContentType().equals(type.name()))
                .map(PostsContent::getText)
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

    @Getter
    @AllArgsConstructor
    @Builder
    public static class OrderCodeReviewDto{
        private Long postId;
        private List<PostTimeDto> selectTime;
        private Integer totalPrice;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PostTimeDto {
        private String day;
        private List<Integer> timeList;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PostTimeResponseDto {
        private List<Integer> am;
        private List<Integer> pm;

        public static PostTimeResponseDto timeResponseDto(List<Integer> timeList){
            return PostTimeResponseDto.builder()
                    .am(timeList.stream().filter(time->time<12).collect(Collectors.toList()))
                    .pm(timeList.stream().filter(time->time>=12).collect(Collectors.toList()))
                    .build();
        }
    }
}
