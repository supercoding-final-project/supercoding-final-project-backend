package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Posts;
import com.github.supercodingfinalprojectbackend.entity.PostsContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PostResponse{

        private String title;
        private String level;
        private Integer price;
        private String post_stack;
        private List<String> text;

        public static PostResponse PostInfoResponse(Posts posts, List<PostsContent> postsContent, String skillStack) {
//            List<String> textList = postsContent.stream()
//                    .sorted(Comparator.comparing(PostsContent::getLocation))
//                    .collect(Collectors.toList()).stream()
//                    .map(PostsContent::getText)
//                    .collect(Collectors.toList());
//
//            return PostResponse.builder()
//                    .title(posts.getTitle())
//                    .level(posts.getLevel())
//                    .price(posts.getPrice())
//                    .post_stack(skillStack)
//                    .text(textList)
//                    .build();
            return null;
        }
    }
}
