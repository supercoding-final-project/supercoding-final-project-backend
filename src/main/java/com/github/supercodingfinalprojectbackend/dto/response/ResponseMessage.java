package com.github.supercodingfinalprojectbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private Long senderId;
    private LocalDateTime sendAt;
    private String text;
}
