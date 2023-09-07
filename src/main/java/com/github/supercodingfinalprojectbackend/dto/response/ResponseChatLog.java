package com.github.supercodingfinalprojectbackend.dto.response;

import lombok.Data;

@Data
public class ResponseChatLog {
    private String chatContent;
    private Long senderId;
    private Long chatroomId;
    private String sendAt;
}