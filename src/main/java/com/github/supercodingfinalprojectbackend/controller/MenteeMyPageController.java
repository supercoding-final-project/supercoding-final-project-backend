package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MenteeMyPageDto;
import com.github.supercodingfinalprojectbackend.service.MenteeMyPageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentee/mypage")
@Slf4j
public class MenteeMyPageController {

    private final MenteeMyPageService myPageService;

    @Operation(summary = "멘티 닉네임 변경")
    @PostMapping("/information")
    public ResponseEntity<?> changeUserInfo(@RequestBody MenteeMyPageDto myPageDto){
        return myPageService.changeNickName(myPageDto);
    }

    @Operation(summary = "멘티 주문내역 조회")
    @GetMapping("/orders")
    public ResponseEntity<?> getOrderList(@RequestParam Long userId){
        return myPageService.getOrderList(userId);
    }

    @Operation(summary = "멘티 결제내역 조회")
    @GetMapping("/transaction")
    public ResponseEntity<?> getMenteeTransactionList(@RequestParam Long userId){
        return myPageService.getMenteeTransactionList(userId);
    }
    @Operation(summary = "멘티 일정표 조회")
    @GetMapping("/templates")
    public ResponseEntity<?> getMenteeCalendersList(@RequestBody MenteeMyPageDto.RequestCalendersList requestCalendersList){
        return myPageService.getMenteeCalendersList(requestCalendersList);
    }

}
