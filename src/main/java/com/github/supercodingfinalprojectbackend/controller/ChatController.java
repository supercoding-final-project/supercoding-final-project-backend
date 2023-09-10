package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MessageDto;
import com.github.supercodingfinalprojectbackend.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{chatroomId}")
    @SendTo("/chatroom/{chatroomId}")
    public MessageDto.ResponseMessage getMessage(@DestinationVariable Long chatroomId, MessageDto message) throws InterruptedException {
        log.info(message.getChatContent());

        MessageDto.ResponseMessage responseMessage = chatService.createMessage(chatroomId, message);
        return responseMessage;
    }
}