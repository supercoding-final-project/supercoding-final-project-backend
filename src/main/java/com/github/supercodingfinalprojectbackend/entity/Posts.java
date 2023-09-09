package com.github.supercodingfinalprojectbackend.entity;

import com.github.supercodingfinalprojectbackend.dto.Post.PostCreateDto;
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
@Table(name = "posts")
public class Posts extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;
    @Column(name = "title")
    private String title;
    @Column(name = "level")
    private String level;
    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    public static Posts fromDto(PostCreateDto postCreateDto, Mentor mentor){
        return Posts.builder()
                .title(postCreateDto.getTitle())
                .level(postCreateDto.getLevel())
                .price(postCreateDto.getPrice())
                .mentor(mentor)
                .build();
    }

}