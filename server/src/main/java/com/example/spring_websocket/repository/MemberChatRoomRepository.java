package com.example.spring_websocket.repository;

import com.example.spring_websocket.domain.ChatRoom;
import com.example.spring_websocket.domain.Member;
import com.example.spring_websocket.domain.MemberChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, Long> {
    List<MemberChatRoom> findByMember(Member member);

    void deleteAllByMember(Member member);

    void deleteByMemberAndChatRoom(Member member, ChatRoom chatRoom);
}
