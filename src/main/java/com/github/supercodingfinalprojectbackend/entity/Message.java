package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Setter
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id",nullable = false)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Column(name = "message_context")
    private String messageContext;

    @CreationTimestamp
    @Column(name = "send_at")
    private Timestamp sendAt;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "is_check",  columnDefinition = "tinyint default 0")
    private Boolean isCheck = false;

    @Column(name = "send_at_front")
    private String sendAtFront;

}
