package com.github.supercodingfinalprojectbackend.dto;

import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import lombok.*;

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
    public static class OrderSheetIdRequest {
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
}
