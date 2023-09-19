package com.github.supercodingfinalprojectbackend.dto.event;

import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import com.github.supercodingfinalprojectbackend.entity.type.OrderState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderEvent {
    private Long orderSheetId;
    private Long postId;
    private Long mentorId;
    private String state;

    public static OrderEvent of(OrderSheet orderSheet, OrderState orderState) {
        Long orderSheetId = orderSheet.getOrderSheetId();
        Long postId = orderSheet.getPost().getPostId();
        Long mentorId = orderSheet.getPost().getMentor().getMentorId();

        return new OrderEvent(orderSheetId, postId, mentorId, orderState.toString());
    }

    public static OrderEvent applied(OrderSheet orderSheet) {
        return of(orderSheet, OrderState.APPLIED);
    }
    public static OrderEvent approved(OrderSheet orderSheet) {
        return of(orderSheet, OrderState.APPROVED);
    }
    public static OrderEvent rejected(OrderSheet orderSheet) {
        return of(orderSheet, OrderState.REJECTED);
    }
    public static OrderEvent canceled(OrderSheet orderSheet) {
        return of(orderSheet, OrderState.CANCELED);
    }
}