package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {


    Optional<Page<Message>> findAllByChatRoom(ChatRoom chatRoom, Pageable pageable);

    Message findAllByMessageIdAndIsCheckIsFalse(Long messageId);
    Message findTopByChatRoomOrderBySendAtDesc(ChatRoom chatRoom);
}
