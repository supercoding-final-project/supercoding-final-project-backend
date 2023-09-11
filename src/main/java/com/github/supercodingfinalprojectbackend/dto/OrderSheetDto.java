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
    private Post postDto;
    private MenteeDto menteeDto;
    private Long totalPrice;
    private Boolean isCompleted;

    public static OrderSheetDto from(OrderSheet orderSheet) {
        return OrderSheetDto.builder()
                .orderSheetId(orderSheet.getOrderSheetId())
                .postDto(Post.from(orderSheet.getPost()))
                .menteeDto(MenteeDto.from(orderSheet.getMentee()))
                .totalPrice(orderSheet.getTotlaPrice().longValue())
                .isCompleted(orderSheet.getIsCompleted())
                .build();
    }
}
