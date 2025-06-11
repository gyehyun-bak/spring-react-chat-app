package com.example.spring_websocket.chatroom.dto.response;

import com.example.spring_websocket.chatroom.ChatRoom;
import lombok.Data;

@Data
public class ChatRoomResponseDto {
    private Long id;
    private String name;
    private int memberCount;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.name = chatRoom.getName();
        this.memberCount = chatRoom.getMemberChatRooms().size();
    }
}
