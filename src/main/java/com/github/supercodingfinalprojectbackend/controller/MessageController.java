package com.github.supercodingfinalprojectbackend.controller;


import com.github.supercodingfinalprojectbackend.entity.ChatRoom;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.repository.ChatRoomRepository;
import com.github.supercodingfinalprojectbackend.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class MessageController {
    private final MessageService messageService;
    private final ChatRoomRepository chatRoomRepository;

    @Operation(summary = "메세지 리스트 반환 기능")
    @GetMapping("/message")
    public ResponseEntity<?> getMessageByChatRoom(
            @RequestParam Long ChatRoomId, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size){

        ChatRoom chatRoom = chatRoomRepository.findByChatRoomIdAndIsChatIsFalse(ChatRoomId).orElseThrow(ApiErrorCode.CHATROOMID_NOT_FOUND::exception);
        Pageable pageable = PageRequest.of(page,size, Sort.by("sendAt").descending());

        return messageService.getMessageByChatroom(chatRoom,pageable);
    }
}
