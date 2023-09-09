package com.github.supercodingfinalprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class Post {
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
