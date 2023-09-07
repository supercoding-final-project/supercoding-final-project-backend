package com.github.supercodingfinalprojectbackend.dto.request;

import lombok.Data;

@Data
public class CreateChatRoomRequest {
    private Long user1Idx;
    private Long user2Idx;
}
