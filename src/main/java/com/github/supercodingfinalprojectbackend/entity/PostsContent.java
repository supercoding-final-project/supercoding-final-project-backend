package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts_content")
public class PostsContent extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_content_id", nullable = false)
    private Integer postContentId;

    @Column(name = "text")
    private String text;

    @Column(name = "location")
    private String location;
}
