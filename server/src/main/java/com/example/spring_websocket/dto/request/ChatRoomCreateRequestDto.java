package com.example.spring_websocket.dto.request;

import lombok.Data;

@Data
public class ChatRoomCreateRequestDto {
    private String accessToken;
    private String name;
}
