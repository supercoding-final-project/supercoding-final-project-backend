package com.github.supercodingfinalprojectbackend.controller;


import com.github.supercodingfinalprojectbackend.dto.MessageDto;
import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import com.github.supercodingfinalprojectbackend.service.ChatRoomService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/v1")
@Slf4j
public class ChatRoomController {


    private final UserRepository userRepository;

    private final ChatRoomService chatRoomService;


    @PostMapping("/createchat")
    @Operation(summary = "채팅방 생성")
    public ResponseEntity<?> createChatRoom(@RequestBody MessageDto.CreateChatRoomRequest chatRoomRequest) {
        // 현재 로그인한 사용자 확인
        User user1 = userRepository.findByUserIdAndIsDeletedIsFalse(chatRoomRequest.getUser1Idx()).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        User user2 = userRepository.findByUserIdAndIsDeletedIsFalse(chatRoomRequest.getUser2Idx()).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
        ChatRoom chatRoom = chatRoomService.createChatroom(user1, user2);
        return ResponseUtils.ok("성공적으로 채팅방이 생성 되었습니다", chatRoom.getChatRoomId());
    }

    @GetMapping("/chatrooms")
    @Operation(summary = "채팅방 리스트 조회")
    public ResponseEntity<?> getChatRoomList(@RequestParam Long userId) {
        MessageDto.ResponseChatRoom chatRoomResponse = chatRoomService.getChatList(userId);
        return ResponseUtils.ok("채팅 리스트 반환에 성공하였습니다",chatRoomResponse);
    }

    /*@GetMapping("/chatlog")
    public ResponseEntity<?> enterChatRoom(@RequestParam Long chatRoomId) {
        MessageDto.ResponseEnterChatRoom chatRoom = chatRoomService.getEnterChatRoom(chatRoomId);
        return ResponseUtils.ok("채팅에 참여하였습니다",chatRoom); 페이지 네이션 기능 구현으로 인한 주석처리!(혹시 모르니 냅두겠습니다)
    } */
}
