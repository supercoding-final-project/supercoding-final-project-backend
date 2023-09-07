package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.request.MessageDto;
import com.github.supercodingfinalprojectbackend.dto.response.ResponseMessage;
import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.entity.Message;
import com.github.supercodingfinalprojectbackend.repository.ChatRoomRepository;
import com.github.supercodingfinalprojectbackend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;

@Slf4j
@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageRepository messageRepository;

    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("/message/{chatroomId}")
    @SendTo("/user/messages")
    public ResponseMessage getMessage(@DestinationVariable Long chatroomId, MessageDto message) throws InterruptedException {
        log.info(String.valueOf(chatroomId));
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(message.getChatroomId());
        log.info(String.valueOf(chatroomId));
        Message messageEntity = Message.builder()
                .isCheck(false)
                .messageContext(message.getText())
                .senderIdx(message.getSenderId())
                .chatRoom(chatRoom)
                .sendAtFront(message.getSendAt())
                .build();
        messageRepository.save(messageEntity);
        log.info(message.getText());
        ResponseMessage responseMessage = new ResponseMessage(message.getSenderId(), LocalDateTime.now(), HtmlUtils.htmlEscape(message.getText()));
        return responseMessage;
    }
}