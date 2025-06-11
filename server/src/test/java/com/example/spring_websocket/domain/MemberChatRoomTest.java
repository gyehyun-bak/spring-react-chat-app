package com.example.spring_websocket.domain;

import com.example.spring_websocket.chatroom.ChatRoom;
import com.example.spring_websocket.member.Member;
import com.example.spring_websocket.memberchatroom.MemberChatRoom;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberChatRoomTest {

    @Test
    void memberJoinsChatRoom() {
        // given
        Member host = Member.createMember("member");
        ChatRoom chatRoom = ChatRoom.createChatRoom("chatRoom", host);

        Member guest = Member.createMember("guest");

        // when
        MemberChatRoom memberChatRoom = MemberChatRoom.memberJoinsChatRoom(guest, chatRoom);

        // then
        assertThat(memberChatRoom.getMember()).isEqualTo(guest);
        assertThat(chatRoom.getMemberChatRooms().size()).isEqualTo(2);
        assertThat(chatRoom.getMemberChatRooms().get(0).getMember()).isEqualTo(host);
        assertThat(chatRoom.getMemberChatRooms().get(1).getMember()).isEqualTo(guest);
    }
}