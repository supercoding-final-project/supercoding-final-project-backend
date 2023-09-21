package com.github.supercodingfinalprojectbackend.controller;


import com.github.supercodingfinalprojectbackend.dto.MessageDto;
import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.User;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.MentorRepository;
import com.github.supercodingfinalprojectbackend.repository.UserRepository;
import com.github.supercodingfinalprojectbackend.service.ChatRoomService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/v1")
@Tag(name = "채팅방 API")
@Slf4j
public class ChatRoomController {


    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;

    private final ChatRoomService chatRoomService;


    @PostMapping("/createchat")
    @Operation(summary = "채팅방 생성")
    public ResponseEntity<?> createChatRoom(@RequestBody MessageDto.CreateChatRoomRequest chatRoomRequest) {
        Long userId = AuthUtils.getUserId();
        User user1 = userRepository.findByUserIdAndIsDeletedIsFalse(userId).orElseThrow(ApiErrorCode.NOT_FOUND_USER::exception);
//        Mentor mentor = mentorRepository.findByMentorIdAndIsDeletedIsFalse(chatRoomRequest.getMentorId()).orElseThrow(ApiErrorCode.NOT_FOUND_MENTOR::exception);
        Mentor mentor = mentorRepository.findById(chatRoomRequest.getMentorId()).orElseThrow(ApiErrorCode.NOT_FOUND_MENTOR::exception);
        User user2 = mentor.getUser();
        ChatRoom chatRoom = chatRoomService.createChatroom(user1, user2);
        return ResponseUtils.ok("성공적으로 채팅방이 생성 되었습니다", chatRoom.getChatRoomId());
    }

    @GetMapping("/chatrooms")
    @Operation(summary = "채팅방 리스트 조회")
    public ResponseEntity<?> getChatRoomList() {
        Long userId = AuthUtils.getUserId();
        MessageDto.ResponseChatRoom chatRoomResponse = chatRoomService.getChatList(userId);
        return ResponseUtils.ok("채팅 리스트 반환에 성공하였습니다",chatRoomResponse);
    }
}
