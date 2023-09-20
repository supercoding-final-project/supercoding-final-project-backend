package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "userId", callSuper = false)
@Table(name = "users")
public class User extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @OneToOne
    @JoinColumn(name = "abstract_account_id")
    private UserAbstractAccount abstractAccount;
    @Column(name = "email")
    private String email;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "thumbnail_image_url")
    private String thumbnailImageUrl;

    public static User of(UserAbstractAccount userAbstractAccount, String email, String nickname, String thumbnailImageUrl) {
        return User.builder()
                .abstractAccount(userAbstractAccount)
                .email(email)
                .nickname(nickname)
                .thumbnailImageUrl(thumbnailImageUrl)
                .build();
    }

    public static User dummy(int number, UserAbstractAccount dummyAbstractAccount) {
        return User.builder()
                .abstractAccount(dummyAbstractAccount)
                .email(String.format("dummy%d@email.com", number))
                .nickname(String.format("dummy%d", number))
                .thumbnailImageUrl("https://code-velop.s3.ap-northeast-2.amazonaws.com/%EB%B0%B0%EC%9A%B0%20%ED%94%84%EB%A1%9C%ED%95%84%201.jpg")
                .build();
    }

    public void changeInfo(String nickname, String email, String thumbnailImageUrl) {
        this.nickname = nickname;
        this.email = email;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public void changeThumbnailImageUrl(String imageUrl){
        if (imageUrl!= null) this.thumbnailImageUrl = imageUrl;
    }
    public void changeUserNameNickname(String nickname){
        this.nickname = nickname;
    }
}
