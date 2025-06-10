package com.example.spring_websocket.repository;

import com.example.spring_websocket.domain.ChatRoom;
import com.example.spring_websocket.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    void deleteByChatRoom(ChatRoom chatRoom);
}
