package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MenteeMyPageDto;
import com.github.supercodingfinalprojectbackend.service.MenteeMyPageService;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentee/mypage")
@Tag(name = "멘티마이페이지 API")
@Slf4j
public class MenteeMyPageController {

    private final MenteeMyPageService myPageService;

    @Operation(summary = "멘티 닉네임 변경")
    @PostMapping("/information")
    public ResponseEntity<?> changeUserInfo(@RequestParam String menteeNickname){
        Long userId = AuthUtils.getUserId();
        return myPageService.changeNickName(userId,menteeNickname);
    }

    @Operation(summary = "멘티 주문내역 조회")
    @GetMapping("/orders")
    public ResponseEntity<?> getOrderList(){
        Long userId = AuthUtils.getUserId();
        return myPageService.getOrderList(userId);
    }

    @Operation(summary = "멘티 결제내역 조회")
    @GetMapping("/transaction")
    public ResponseEntity<?> getMenteeTransactionList(){
        Long userId = AuthUtils.getUserId();
        return myPageService.getMenteeTransactionList(userId);
    }
    @Operation(summary = "멘티 일정표 조회")
    @GetMapping("/templates")
    public ResponseEntity<?> getMenteeCalendersList(@RequestBody MenteeMyPageDto.RequestCalendersList requestCalendersList){
        return myPageService.getMenteeCalendersList(requestCalendersList);
    }
    @Operation(summary = "멘티 이미지 변경")
    @PostMapping("/changeimage")
    public ResponseEntity<?> changeUserImage(MultipartFile file){
        Long userId = AuthUtils.getUserId();
        return myPageService.changeUserImage(userId,file);
    }
}
