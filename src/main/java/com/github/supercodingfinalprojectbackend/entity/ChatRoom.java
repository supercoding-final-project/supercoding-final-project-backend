package com.github.supercodingfinalprojectbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "chat_room_id",nullable = false)
    private Long chatRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @Column(name = "chat_name")
    private String  chatName;

    @Column(name = "is_chat")
    private Boolean isChat;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
}
