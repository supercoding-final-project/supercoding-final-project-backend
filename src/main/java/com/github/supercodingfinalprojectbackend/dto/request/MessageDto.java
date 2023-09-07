package com.github.supercodingfinalprojectbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long senderId;
    private Long chatroomId;
    private String sendAt;
    private String text;
}
