package com.github.supercodingfinalprojectbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.supercodingfinalprojectbackend.entity.Posts;
import com.github.supercodingfinalprojectbackend.entity.PostsContent;
import com.github.supercodingfinalprojectbackend.entity.SkillStack;
import com.github.supercodingfinalprojectbackend.entity.type.PostContentType;
import lombok.*;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PostDto {
    private Long postId;
    @NotBlank
    private String title;
    @NotBlank
    private String level;
    @NotNull
    private Integer price;
    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    public static PostDto of(Posts posts, List<PostsContent> postsContent) {
        List<String> workCareerList = postContentToList(postsContent, PostContentType.WORK_CAREER);
        List<String> educateCareerList = postContentToList(postsContent, PostContentType.EDUCATE_CAREER);
        List<String> reviewStyleList = postContentToList(postsContent, PostContentType.REVIEW_STYLE);

        return PostDto.builder()
                .postId(posts.getPostId())
                .title(posts.getTitle())
                .level(posts.getLevel())
                .price(posts.getPrice())
                .workCareer(workCareerList)
                .educateCareer(educateCareerList)
                .reviewStyle(reviewStyleList)
                .build();
    }

    public static List<String> postContentToList(List<PostsContent> postsContents, PostContentType type) {
        return postsContents.stream()
                .filter(contentType -> contentType.getContentType().equals(type.name()))
                .map(PostsContent::getText)
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PostInfoResponse {
        private PostDto postInfo;
        private String postStack;
        private String stackCategory;
        private String skillStackImg;
        private boolean permission;
        private Long mentorId;

        public static PostInfoResponse of(Posts post, List<PostsContent> contentList, SkillStack skillStack, boolean permission) {
            return PostInfoResponse.builder()
                    .postInfo(PostDto.of(post,contentList))
                    .postStack(skillStack.getSkillStackName())
                    .skillStackImg(skillStack.getSkillStackImg())
                    .stackCategory(skillStack.getSkillStackCategory())
                    .permission(permission)
                    .mentorId(post.getMentor().getMentorId())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class OrderCodeReviewDto {
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

        public static PostTimeResponseDto timeResponseDto(List<Integer> timeList) {
            return PostTimeResponseDto.builder()
                    .am(timeList.stream().filter(time -> time <= 12).collect(Collectors.toList()))
                    .pm(timeList.stream().filter(time -> time > 12).map(time -> time - 12).collect(Collectors.toList()))
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PostSearchDto {
        private List<PostInfoResponse> postList;
        private int totalPage;
        private int nowPage;
        private boolean hasPrevious;
        private boolean hasNext;

        public static PostSearchDto searchDto(List<PostInfoResponse> postDtoList, Page page) {
            return PostSearchDto.builder()
                    .postList(postDtoList)
                    .totalPage(page.getTotalPages())
                    .nowPage(page.getNumber() + 1)
                    .hasPrevious(page.hasPrevious())
                    .hasNext(page.hasNext())
                    .build();
        }
    }
}

