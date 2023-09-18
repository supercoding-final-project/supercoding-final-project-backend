package com.github.supercodingfinalprojectbackend.service;


import com.github.supercodingfinalprojectbackend.dto.MessageDto;
import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.entity.Message;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.ChatRoomRepository;
import com.github.supercodingfinalprojectbackend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final MessageRepository messageRepository ;

    private final ChatRoomRepository chatRoomRepository;

    public MessageDto.ResponseMessage createMessage(Long chatroomId , MessageDto message){

        ChatRoom chatRoom = chatRoomRepository.findByChatRoomIdAndIsChatIsFalse(chatroomId).orElseThrow(ApiErrorCode.CHATROOMID_NOT_FOUND::exception);

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);

        Message messageEntity = Message.builder()
                .isCheck(false)
                .messageContext(message.getChatContent())
                .senderId(message.getSenderId())
                .chatRoom(chatRoom)
                .sendAtFront(message.getSendAt())
                .build();

        messageRepository.save(messageEntity);
        return new MessageDto.ResponseMessage(message.getSenderId(), message.getSendAt(), HtmlUtils.htmlEscape(message.getChatContent()),formattedDate);
    }
}
