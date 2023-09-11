package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.Posts;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Post {
    private Long postId;
    private MentorDto mentorDto;
    private String title;
    private String level;
    private Integer price;

    public static Post from(Posts post) {
        return Post.builder()
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
    public static class PostCreateDto {

    @NotBlank
    private String title;
    @NotBlank
    private String level;
    @NotNull
    private Integer price;
    @NotBlank
    private String post_stack;
    @NotNull
    @Size(min = 4,max = 4)
    private List<String> text;
    @NotNull
    @Size(min = 4,max = 4)
    private List<MultipartFile> img;
    }
}
