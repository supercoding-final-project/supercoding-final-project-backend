package com.github.supercodingfinalprojectbackend.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id", nullable = false)
    private Long chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private User user2;

    @Column(name = "chat_name")
    private String chatName;

    @Column(name = "is_chat")
    private Boolean isChat;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;
}
