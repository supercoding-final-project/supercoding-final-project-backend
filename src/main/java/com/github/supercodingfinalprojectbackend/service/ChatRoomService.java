package com.github.supercodingfinalprojectbackend.service;


import com.github.supercodingfinalprojectbackend.dto.MessageDto;
import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.entity.Message;
import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.ChatRoomRepository;
import com.github.supercodingfinalprojectbackend.repository.MessageRepository;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    //  채팅방 생성

    public ChatRoom createChatroom(User user1, User user2) {


        List<ChatRoom> chatRooms = chatRoomRepository.findByUser1OrUser2(user1, user2).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        for (ChatRoom chatRoom : chatRooms) {
            if (chatRoom.getUser1().equals(user1) && chatRoom.getUser2().equals(user2)) {
                // 중복 채팅방이 이미 존재하면 해당 채팅방을 반환
                return chatRoom;
            }
        }
        ChatRoom chatRoom = ChatRoom.builder().user1(user1).user2(user2).chatName(createChatName(user1, user2)).isChat(false).build();
        return chatRoomRepository.save(chatRoom);
    }
    // User 에 관련된 채팅방 목록 조회
    public MessageDto.ResponseChatRoom getChatList(Long userid) {


        User user = userRepository.findByUserIdAndIsDeletedIsFalse(userid).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        List<ChatRoom> chatRooms = chatRoomRepository.findByUser1OrUser2(user, user).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);

        List<MessageDto.ResponseChatRoomInfo> chatRoomInfoList = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms){
            MessageDto.ResponseChatRoomInfo chatRoomInfo = new MessageDto.ResponseChatRoomInfo();

            // 채팅방 정보 설정
            chatRoomInfo.setChatroomId(chatRoom.getChatRoomId());

            // 여기에서 채팅방의 마지막 채팅과 읽지 않은 메시지 수를 얻어옵니다.
            Message lastMessage = messageRepository.findTopByChatRoomOrderBySendAtDesc(chatRoom);
            if (lastMessage != null) {
                chatRoomInfo.setLastChat(lastMessage.getMessageContext());
                //TODO : 읽지 않은 메세지 수를 카운트하여 프론트에 넘겨주는 기능 개발 예정.
//                int unreadCount = 0;
            }

            // 사용자 프로필 사진 및 상대방 닉네임 설정
            User partner = chatRoom.getUser1().getUserId() == userid ? chatRoom.getUser2() : chatRoom.getUser1();
            chatRoomInfo.setProfileImg(partner.getThumbnailImageUrl());
            chatRoomInfo.setPartnerName(partner.getNickname());

            chatRoomInfoList.add(chatRoomInfo);
        }
        MessageDto.ResponseChatRoom chatRoomResponse = new MessageDto.ResponseChatRoom();
        chatRoomResponse.setChatRoom(chatRoomInfoList);
        return  chatRoomResponse;
    }
    public String createChatName(User user1, User user2){
        return user1.getNickname() + " " + user2.getNickname();
    }

 /*   public MessageDto.ResponseEnterChatRoom getEnterChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomIdAndIsChatIsFalse(chatRoomId).orElseThrow(ApiErrorCode.ChatRoomId_NOT_FOUND::exception);

        List<Message> messages = messageRepository.findByChatRoom(chatRoom, Sort.by(Sort.Order.asc("sendAt")));
        List<MessageDto.ResponseChatLog> chatMessages = new ArrayList<>();

        for (Message message : messages) {
            MessageDto.ResponseChatLog chatMessageDto = new MessageDto.ResponseChatLog();
            chatMessageDto.setChatContent(message.getMessageContext());
            chatMessageDto.setSenderId(message.getSenderId());
            chatMessageDto.setSendAt(String.valueOf(message.getSendAtFront()));
            chatMessageDto.setChatroomId(chatRoom.getChatRoomId());
            chatMessages.add(chatMessageDto);
        }

        MessageDto.ResponseEnterChatRoom enterChatRoom = MessageDto.ResponseEnterChatRoom.builder()
                .chatLog(chatMessages)
                .build();

        return enterChatRoom;
    }페이지 네이션 기능 구현으로 인한 주석처리!(혹시 모르니 냅두겠습니다) */
}
