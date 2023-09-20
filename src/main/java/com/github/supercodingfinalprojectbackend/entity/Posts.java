package com.github.supercodingfinalprojectbackend.entity;

import com.github.supercodingfinalprojectbackend.dto.PostDto;
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
    @Column(name = "star")
    private Float star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    public static Posts fromDto(PostDto postDto, Mentor mentor){
        return Posts.builder()
                .title(postDto.getTitle())
                .level(postDto.getLevel())
                .price(postDto.getPrice())
                .mentor(mentor)
                .build();
    }

    public void postsUpdate(PostDto postDto){
        this.title = postDto.getTitle();
        this.level = postDto.getLevel();
        this.price = postDto.getPrice();
    }

    public void postsIsDeleted(){
        this.isDeleted = true;
    }

    public void updateStar(Float star) {
        this.star = star;
    }



}