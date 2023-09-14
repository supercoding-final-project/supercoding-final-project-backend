package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.OrderSheetDto;
import com.github.supercodingfinalprojectbackend.dto.PaymentDto;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.service.OrderService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/approve")
    @Operation(summary = "주문서 결제 승인")
    public ResponseEntity<ResponseUtils.ApiResponse<PaymentDto.PaymentIdResponse>> approveOrder(@RequestBody OrderSheetDto.OrderSheetIdRequest request) {
        ValidateUtils.requireTrue(request.validate(), ApiErrorCode.INVALID_REQUEST_BODY);

        Long userId = AuthUtils.getUserId();
        PaymentDto.PaymentIdResponse response = orderService.approveOrder(userId, request);

        return ResponseUtils.ok("결제 요청을 성공적으로 승인했습니다.", response);
    }

    @PostMapping("/refuse")
    @Operation(summary = "주문서 결제 반려")
    public ResponseEntity<ResponseUtils.ApiResponse<OrderSheetDto.OrderSheetIdResponse>> refuseOrder(@RequestBody OrderSheetDto.OrderSheetIdRequest request) {
        ValidateUtils.requireTrue(request.validate(), ApiErrorCode.INVALID_REQUEST_BODY);

        Long userId = AuthUtils.getUserId();
        OrderSheetDto.OrderSheetIdResponse response = orderService.refuseOrder(userId, request);

        return ResponseUtils.ok("결제 요청을 성공적으로 취소하였습니다.", response);
    }
}
