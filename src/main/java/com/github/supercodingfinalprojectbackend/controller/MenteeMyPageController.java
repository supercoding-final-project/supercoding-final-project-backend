package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MenteeMyPageDto;
import com.github.supercodingfinalprojectbackend.service.MenteeMyPageService;
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

    @PostMapping("/information")
    public ResponseEntity<?> changeUserInfo(@RequestBody MenteeMyPageDto myPageDto){
        return myPageService.changeNickName(myPageDto);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrderList(@RequestParam Long userId){
        return myPageService.getOrderList(userId);
    }

    public ResponseEntity<?> getMenteeTransactionList(@RequestParam Long userId){
        return myPageService.getMenteeTransactionList(userId);
    }
}
