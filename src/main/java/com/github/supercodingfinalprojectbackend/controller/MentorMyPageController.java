package com.github.supercodingfinalprojectbackend.controller;


import com.github.supercodingfinalprojectbackend.service.MentorMyPageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/mentor/mypage")
public class MentorMyPageController {

    private final MentorMyPageService mentorMyPageService;
    @Operation(summary = "멘토 주문내역 조회")
    @GetMapping("/orders")
    public ResponseEntity<?> getMentorOrderList(@RequestParam Long userId){
//        Long userId = AuthUtils.getUserId();
        return mentorMyPageService.getOrderList(userId);
    }
    @Operation(summary = "멘토 거래내역 조회")
    @GetMapping("/transaction")
    public ResponseEntity<?> getMentorTransactionList(@RequestParam Long userId){
//        Long userId = AuthUtils.getUserId();
        return mentorMyPageService.getTransactionList(userId);
    }
}
