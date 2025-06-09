package com.example.spring_websocket.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {

    @Test
    void createMessage() {
        // given
        String content = "content";
        User user = User.createUser("user");
        ChatRoom chatRoom = ChatRoom.createChatRoom("chatRoom", user);

        // when
        Message message = Message.createMessage(user, chatRoom, content);

        // then
        assertThat(message.getContent()).isEqualTo(content);
        assertThat(message.getCreatedBy()).isEqualTo(user);
        assertThat(message.getChatRoom()).isEqualTo(chatRoom);
    }
}