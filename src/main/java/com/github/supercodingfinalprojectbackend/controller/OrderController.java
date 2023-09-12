package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.OrderSheetDto;
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
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/approve")
    @Operation(summary = "주문서 결제 승인")
    public ResponseEntity<ResponseUtils.ApiResponse<PaymentDto.PaymentIdResponse>> approveOrder(@RequestBody OrderSheetDto.OrderSheetIdRequest request) {
        Long userId = AuthUtils.getUserId();
        OrderSheetDto orderDtoRequest = OrderSheetDto.from(request);

        PaymentDto paymentDtoResponse = orderService.approveOrder(userId, orderDtoRequest);
        PaymentDto.PaymentIdResponse response = PaymentDto.PaymentIdResponse.from(paymentDtoResponse);

        return ResponseUtils.created("결제 요청을 성공적으로 승인했습니다.", response);
    }

    @PostMapping("/refuse")
    @Operation(summary = "주문서 결제 반려")
    public ResponseEntity<ResponseUtils.ApiResponse<OrderSheetDto.OrderSheetIdResponse>> refuseOrder(@RequestBody OrderSheetDto.OrderSheetIdRequest request) {
        Long userId = AuthUtils.getUserId();
        OrderSheetDto orderDtoRequest = OrderSheetDto.from(request);

        OrderSheetDto orderSheetDto = orderService.refuseOrder(userId, orderDtoRequest);
        OrderSheetDto.OrderSheetIdResponse response = OrderSheetDto.OrderSheetIdResponse.from(orderSheetDto);

        return ResponseUtils.ok("결제 요청을 성공적으로 취소하였습니다.", response);
    }
}
