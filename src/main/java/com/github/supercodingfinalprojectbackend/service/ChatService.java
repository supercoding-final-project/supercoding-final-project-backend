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

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final MessageRepository messageRepository ;

    private final ChatRoomRepository chatRoomRepository;

    public MessageDto.ResponseMessage createMessage(Long chatroomID , MessageDto message){

        ChatRoom chatRoom = chatRoomRepository.findByChatRoomIdAndIsChatIsFalse(chatroomID).orElseThrow(ApiErrorCode.CHATROOMID_NOT_FOUND::exception);

        Message messageEntity = Message.builder()
                .isCheck(false)
                .messageContext(message.getChatContent())
                .senderId(message.getSenderId())
                .chatRoom(chatRoom)
                .sendAtFront(message.getSendAt())
                .build();
        messageRepository.save(messageEntity);
        MessageDto.ResponseMessage responseMessage = new MessageDto.ResponseMessage(message.getSenderId(), message.getSendAt(), HtmlUtils.htmlEscape(message.getChatContent()));
        return responseMessage;
    }
}