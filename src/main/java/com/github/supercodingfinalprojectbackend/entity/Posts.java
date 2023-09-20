package com.github.supercodingfinalprojectbackend.entity;

import com.github.supercodingfinalprojectbackend.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

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

    public static Posts dummy(Mentor dummyMentor) {

        return Posts.builder()
                .mentor(dummyMentor)
                .title("천상천하유아독존 " + dummyMentor.getUser().getNickname() + "님의 코드리뷰! 이런 기회는 흔하지 않다구~ ")
                .level(randomLevel())
                .price(randomPrice())
                .star(randomStar())
                .build();
    }

    private static Float randomStar() {
        final float min = 0.0f;
        final float max = 5.0f;
        final float value = min + (max - min) * new Random().nextFloat();
        return Math.round(value * 10) / 10.0f;
    }

    private static Integer randomPrice() {
        return (new Random().nextInt(490) + 10) * 1000;
    }

    private static String randomLevel() {
        String[] levels = {"입문", "초급", "중급", "상급", "최상"};
        return levels[new Random().nextInt(levels.length)];
    }
}