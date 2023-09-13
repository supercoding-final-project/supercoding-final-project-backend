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
    @Column(name = "name")
    private String name;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "thumbnail_image_url")
    private String thumbnailImageUrl;

    public void changeInfo(String nickname, String email) {
        if (nickname != null) this.nickname = nickname;
        if (email != null) this.email = email;
    }

    public void changeUserNameNickname(String nickname){
        this.nickname = nickname;
    }
}
