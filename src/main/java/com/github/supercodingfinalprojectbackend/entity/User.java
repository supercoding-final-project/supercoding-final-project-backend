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

    public void changeInfo(String nickname, String email) {
        if (nickname != null) this.nickname = nickname;
        if (email != null) this.email = email;
    }

    public void changeUserImage(String imageUrl){
        if (imageUrl!= null) this.thumbnailImageUrl = imageUrl;
    }
    public void changeUserNameNickname(String nickname){
        this.nickname = nickname;
    }
}
