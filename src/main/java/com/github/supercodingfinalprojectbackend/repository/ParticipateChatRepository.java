package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.ParticipateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipateChatRepository extends JpaRepository<ParticipateChat, Integer> {
}
