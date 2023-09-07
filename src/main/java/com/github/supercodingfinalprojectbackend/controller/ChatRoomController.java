package com.github.supercodingfinalprojectbackend.controller;


import com.github.supercodingfinalprojectbackend.dto.request.ChatRoomListRequest;
import com.github.supercodingfinalprojectbackend.dto.request.CreateChatRoomRequest;
import com.github.supercodingfinalprojectbackend.dto.response.ResponseChatRoom;
import com.github.supercodingfinalprojectbackend.dto.response.ResponseEnterChatRoom;
import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import com.github.supercodingfinalprojectbackend.service.ChatRoomService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/v1")
@Slf4j
public class ChatRoomController {


    private final UserRepository userRepository;

    private final ChatRoomService chatRoomService;


    @PostMapping("/createchat")
    public ResponseEntity<?> createChatRoom(@RequestBody CreateChatRoomRequest chatRoomRequest) {
        // 현재 로그인한 사용자 확인
        User user1 = userRepository.findByUserId(chatRoomRequest.getUser1Idx());
        User user2 = userRepository.findByUserId(chatRoomRequest.getUser2Idx());
        ChatRoom chatRoom = chatRoomService.createChatroom(user1, user2);
        return ResponseUtils.ok("성공적으로 채팅방이 생성 되었습니다", chatRoom.getChatRoomId());
    }

    @GetMapping("/chatrooms")
    public ResponseEntity<?> getChatRoomList(@RequestBody ChatRoomListRequest chatRoomListRequest) {
        ResponseChatRoom chatRoomResponse = chatRoomService.getChatList(chatRoomListRequest.getUserId());
        return ResponseUtils.ok("채팅 리스트 반환에 성공하였습니다",chatRoomResponse);
    }

    @GetMapping("chatroom/{chatroom_id}")
    public ResponseEntity<?> enterChatRoom(@PathVariable("chatroom_id") Long chatRoomId) {
        ResponseEnterChatRoom chatRoom = chatRoomService.getEnterChatRoom(chatRoomId);
        return ResponseUtils.ok("채팅에 참여하였습니다",chatRoom);
    }
}
