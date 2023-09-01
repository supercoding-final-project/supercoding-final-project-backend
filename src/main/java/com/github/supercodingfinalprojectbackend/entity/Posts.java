package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts")
public class Posts extends CommonEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @Column(name = "title")
    private String title;
    @Column(name = "level")
    private String level;
    @Column(name = "review_objective")
    private String reviewObjective;
    @Column(name = "price")
    private Integer price;


}