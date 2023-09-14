package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.MentorCareerDto;
import com.github.supercodingfinalprojectbackend.dto.OrderSheetDto;
import com.github.supercodingfinalprojectbackend.dto.PaymentDto;
import com.github.supercodingfinalprojectbackend.entity.type.UserRole;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.service.OrderService;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import com.github.supercodingfinalprojectbackend.util.auth.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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

    @DeleteMapping("/identifier")
    @Operation(summary = "주문서 결제 취소")
    public ResponseEntity<ResponseUtils.ApiResponse<OrderSheetDto.OrderSheetIdSetResponse>> cancelOrder(
            @RequestParam("id") @Parameter(name = "주문서 아이디", required = true) Set<String> orderSheetIdStringSet
    ) {
        ValidateUtils.requireTrue(AuthUtils.hasRole(UserRole.MENTEE), ApiErrorCode.UNAUTHORIZED);

        Long userId = AuthUtils.getUserId();

        Set<Long> orderSheetIdSet = orderSheetIdStringSet.stream()
                .map(orderSheetIdString->ValidateUtils.requireApply(orderSheetIdString, Long::parseLong, ApiErrorCode.INVALID_REQUEST_PARAMETER))
                .collect(Collectors.toSet());

        OrderSheetDto.OrderSheetIdSetResponse response = orderService.cancelOrders(userId, orderSheetIdSet);
        return ResponseUtils.ok("결제를 성공적으로 취소했습니다.", response);
    }
}
