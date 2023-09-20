package com.github.supercodingfinalprojectbackend.entity;

import com.github.supercodingfinalprojectbackend.entity.type.PostContentType;
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

    @Column(name = "content_type")
    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts posts;

    public static PostsContent fromPost(String text, String contentType, Posts posts ){
        return PostsContent.builder()
                .text(text)
                .contentType(contentType)
                .posts(posts)
                .build();
    }

    public void postContentIsDeleted(){
        this.isDeleted = true;
    }

    public static PostsContent dummy(Posts dummyPosts) {
        return PostsContent.builder()
                .posts(dummyPosts)
                .text(dummyPosts.getMentor().getUser().getNickname() + "의 코드리뷰 소개 들어간다. 내 코드리뷰를 받을 땐 캠 키는 거 필수다. \n마스크 안 된다. 모자 안 된다. 선글라스 안 된다. \n이상이다.")
                .contentType(PostContentType.dummy())
                .build();
    }
}
