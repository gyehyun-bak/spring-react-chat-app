package com.example.spring_websocket.message;

import com.example.spring_websocket.chatroom.ChatRoom;
import com.example.spring_websocket.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    void deleteByChatRoom(ChatRoom chatRoom);
}
