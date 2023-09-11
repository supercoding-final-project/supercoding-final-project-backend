package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.OrderDto;
import com.github.supercodingfinalprojectbackend.dto.PaymentDto;
import com.github.supercodingfinalprojectbackend.service.OrderService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/approve")
    @Operation(summary = "주문서 결제 승인")
    public ResponseEntity<ResponseUtils.ApiResponse<PaymentDto.PaymentIdResponse>> approveOrder(@RequestBody OrderDto.OrderIdRequest request) {
        Long userId = AuthUtils.getUserId();
        OrderDto orderDtoRequest = OrderDto.from(request);

        PaymentDto paymentDtoResponse = orderService.approveOrder(userId, orderDtoRequest);
        PaymentDto.PaymentIdResponse response = PaymentDto.PaymentIdResponse.from(paymentDtoResponse);

        return ResponseUtils.created("결제에 성공했습니다.", response);
    }
}
