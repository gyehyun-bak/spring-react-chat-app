package com.example.spring_websocket.memberchatroom;

import com.example.spring_websocket.chatroom.ChatRoom;
import com.example.spring_websocket.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    public static MemberChatRoom memberJoinsChatRoom(Member member, ChatRoom chatRoom) {
        MemberChatRoom memberChatRoom = new MemberChatRoom();

        member.addMemberChatRoom(memberChatRoom);
        chatRoom.addMemberChatRoom(memberChatRoom);

        return memberChatRoom;
    }
}
