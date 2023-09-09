package com.github.supercodingfinalprojectbackend.repository;


import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<List<ChatRoom>> findByUser1OrUser2(User user1, User user2);
    Optional<ChatRoom> findByChatRoomIdAndIsChatIsFalse(Long chatRoomId);
}