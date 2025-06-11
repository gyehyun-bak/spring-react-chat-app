package com.example.spring_websocket.domain;

import com.example.spring_websocket.chatroom.ChatRoom;
import com.example.spring_websocket.member.Member;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChatRoomTest {

    @Test
    void createChatRoom() {
        // given
        String name = "chatRoom";
        Member host = Member.createMember("host");

        // when
        ChatRoom chatRoom = ChatRoom.createChatRoom(name, host);

        // then
        assertThat(chatRoom.getName()).isEqualTo(name);
        assertThat(chatRoom.getMember()).isEqualTo(host);
        assertThat(chatRoom.getMemberChatRooms().size()).isEqualTo(1);
        assertThat(chatRoom.getMemberChatRooms().get(0).getMember()).isEqualTo(host);
    }
}