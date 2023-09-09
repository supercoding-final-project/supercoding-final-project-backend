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
@Table(name = "posts_content")
public class PostsContent extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_content_id", nullable = false)
    private Long postContentId;

    @Column(name = "text")
    private String text;

    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts posts;

    public static PostsContent fromPost(String text, Integer location, Posts posts ){
        return PostsContent.builder()
                .text(text)
                .location(String.valueOf(location))
                .posts(posts)
                .build();
    }
}
