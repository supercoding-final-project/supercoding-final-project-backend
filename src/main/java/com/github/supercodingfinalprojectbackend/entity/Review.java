package com.github.supercodingfinalprojectbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post;

    @Column(name = "content")
    private String content;

    @Column(name = "star")
    private Integer star;

    @Column(name = "number_of_reviews_received")
    private Integer numberOfReviewsReceived;

    public static Review toEntity(Mentee mentee, Posts posts, String content, Integer star, Integer numberOfReviewsReceived) {
        return Review.builder()
                .mentee(mentee)
                .post(posts)
                .content(content)
                .star(star)
                .numberOfReviewsReceived(numberOfReviewsReceived)
                .build();
    }

    public Boolean isValid(Long menteeId) {
        return !this.isDeleted && Objects.equals(this.mentee.getMenteeId(), menteeId);
    }

    public void delete() {
        this.isDeleted = true;
    }
}
