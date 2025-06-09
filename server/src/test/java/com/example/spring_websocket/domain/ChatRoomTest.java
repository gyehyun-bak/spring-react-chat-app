package com.example.spring_websocket.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChatRoomTest {

    @Test
    void createChatRoom() {
        // given
        String name = "name";
        User host = User.createUser("host");

        // when
        ChatRoom chatRoom = ChatRoom.createChatRoom(name, host);

        // then
        assertThat(chatRoom.getName()).isEqualTo(name);
        assertThat(chatRoom.getCreatedBy()).isEqualTo(host);
    }
}