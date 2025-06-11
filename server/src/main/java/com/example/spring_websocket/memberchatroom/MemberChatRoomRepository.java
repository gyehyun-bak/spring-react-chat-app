package com.example.spring_websocket.memberchatroom;

import com.example.spring_websocket.chatroom.ChatRoom;
import com.example.spring_websocket.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, Long> {
    List<MemberChatRoom> findByMember(Member member);

    void deleteAllByMember(Member member);

    void deleteByMemberAndChatRoom(Member member, ChatRoom chatRoom);

    boolean existsMemberChatRoomByMemberAndChatRoom(Member member, ChatRoom chatRoom);

    long countMemberChatRoomByChatRoom(ChatRoom chatRoom);
}
