package com.example.spring_websocket.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Modifying
    @Query("delete from ChatRoom c where c.memberChatRooms is empty")
    void deleteAllEmptyChatRooms();
}
