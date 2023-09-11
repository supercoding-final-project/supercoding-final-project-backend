package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MenteeMyPageDto;
import com.github.supercodingfinalprojectbackend.service.MenteeMyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
@Slf4j
public class MenteeMyPageController {
    private final MenteeMyPageService myPageService;

    @PostMapping("/information")
    public ResponseEntity<?> changeUserInfo(@RequestBody MenteeMyPageDto myPageDto){
        return myPageService.changeNickName(myPageDto);
    }
}
