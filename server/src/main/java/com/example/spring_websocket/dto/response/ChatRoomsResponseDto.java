package com.example.spring_websocket.dto.response;

import com.example.spring_websocket.domain.ChatRoom;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomsResponseDto {
    private List<ChatRoomResponseDto> chatRooms;

    public ChatRoomsResponseDto(List<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms.stream()
                .map(ChatRoomResponseDto::new)
                .toList();
    }
}
