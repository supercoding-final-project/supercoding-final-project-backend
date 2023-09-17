package com.github.supercodingfinalprojectbackend.service;


import com.github.supercodingfinalprojectbackend.dto.MessageDto;
import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.entity.Message;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MessageRepository;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    public ResponseEntity<?> getMessageByChatroom(ChatRoom chatRoom , Pageable pageable){

        Page<Message> page = messageRepository.findAllByChatRoom(chatRoom,pageable).orElseThrow(ApiErrorCode.CHATROOMID_NOT_FOUND::exception);

        List<MessageDto.ResponseMessage> responseMessages = page.getContent().stream()
                .map(message -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(message.getSendAt());

                    return MessageDto.ResponseMessage.builder()
                            .senderId(message.getSenderId())
                            .sendAt(message.getSendAtFront())
                            .chatContent(message.getMessageContext())
                            .DbSendAt(formattedDate)
                            .build();
                }).
                collect(Collectors.toList());

        Collections.reverse(responseMessages);


        return ResponseUtils.ok("채팅로그가 성공적으로 반환되었습니다",responseMessages);
    }
}
