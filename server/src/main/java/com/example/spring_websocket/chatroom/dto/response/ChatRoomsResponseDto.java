package com.example.spring_websocket.chatroom.dto.response;

import com.example.spring_websocket.chatroom.ChatRoom;
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
