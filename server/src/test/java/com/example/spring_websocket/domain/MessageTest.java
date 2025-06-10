package com.example.spring_websocket.domain;

import com.example.spring_websocket.domain.enums.MessageType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {

    @Test
    void createChatMessage() {
        // given
        String content = "content";
        Member member = Member.createMember("member");
        ChatRoom chatRoom = ChatRoom.createChatRoom("chatRoom", member);

        // when
        Message message = Message.createChatMessage(member, chatRoom, content);

        // then
        assertThat(message.getContent()).isEqualTo(content);
        assertThat(message.getMember()).isEqualTo(member);
        assertThat(message.getChatRoom()).isEqualTo(chatRoom);
        assertThat(message.getType()).isEqualTo(MessageType.CHAT);
    }

    @Test
    void createSystemMessage() {
        // given
        String content = "content";
        Member member = Member.createMember("member");
        ChatRoom chatRoom = ChatRoom.createChatRoom("chatRoom", member);

        // when
        Message message = Message.createSystemMessage(member, chatRoom, content);

        // then
        assertThat(message.getContent()).isEqualTo(content);
        assertThat(message.getMember()).isEqualTo(member);
        assertThat(message.getChatRoom()).isEqualTo(chatRoom);
        assertThat(message.getType()).isEqualTo(MessageType.SYSTEM);
    }
}