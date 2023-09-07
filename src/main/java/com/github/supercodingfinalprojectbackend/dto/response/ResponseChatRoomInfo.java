package com.github.supercodingfinalprojectbackend.dto.response;


import lombok.Data;

@Data
public class ResponseChatRoomInfo {
    private Long chatroomId;
    private String lastChat;
    private Integer unreadCount;
    private String profileImg;
    private String partnerName;
}
