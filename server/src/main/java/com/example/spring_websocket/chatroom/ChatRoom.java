package com.example.spring_websocket.chatroom;

import com.example.spring_websocket.global.audit.BaseEntity;
import com.example.spring_websocket.memberchatroom.MemberChatRoom;
import com.example.spring_websocket.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MemberChatRoom> memberChatRooms = new ArrayList<>();

    public static ChatRoom createChatRoom(String name, Member createdBy) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .member(createdBy)
                .build();

        MemberChatRoom.memberJoinsChatRoom(createdBy, chatRoom);

        return chatRoom;
    }

    public void addMemberChatRoom(MemberChatRoom memberChatRoom) {
        memberChatRooms.add(memberChatRoom);
        memberChatRoom.setChatRoom(this);
    }
}
