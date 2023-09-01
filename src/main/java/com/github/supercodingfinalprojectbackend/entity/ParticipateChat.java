package com.github.supercodingfinalprojectbackend.entity;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "participate_chats")
public class ParticipateChat {
    @Id @Column(name = "participate_id")@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer participateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @Column(name = "mentee_id")
    private Integer menteeId;

}
