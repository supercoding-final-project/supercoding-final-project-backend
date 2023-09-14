package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderSheetDto {
    private Long orderSheetId;
    private PostDto postDto;
    private MenteeDto menteeDto;
    private Long totalPrice;
    private Boolean isCompleted;

    public static OrderSheetDto from(OrderSheet orderSheet) {
        return OrderSheetDto.builder()
                .orderSheetId(orderSheet.getOrderSheetId())
                .postDto(PostDto.from(orderSheet.getPost()))
                .menteeDto(MenteeDto.from(orderSheet.getMentee()))
                .totalPrice(orderSheet.getTotlaPrice().longValue())
                .isCompleted(orderSheet.getIsCompleted())
                .build();
    }

    public static OrderSheetDto from(OrderSheetIdRequest request) {
        return OrderSheetDto.builder()
                .orderSheetId(request.orderSheetId)
                .build();
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @Schema(name = "주문서 결제 승인 요청 객체")
    public static class OrderSheetIdRequest {
        @Schema(name = "주문서 아이디")
        private Long orderSheetId;

        public boolean validate() {
            return orderSheetId != null;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class OrderSheetIdResponse {
        private Long orderSheetId;

        public static OrderSheetIdResponse from(OrderSheet orderSheet) {
            return new OrderSheetIdResponse(orderSheet.getOrderSheetId());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class OrderSheetIdSetResponse {
        private Set<Long> orderSheetIds;

        public static OrderSheetIdSetResponse from(List<OrderSheet> orderSheets) {
            Set<Long> orderSheetIdSet = orderSheets.stream()
                    .map(OrderSheet::getOrderSheetId)
                    .collect(Collectors.toSet());

            return new OrderSheetIdSetResponse(orderSheetIdSet);
        }
    }
}
