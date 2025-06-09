package com.example.spring_websocket.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Member createdBy;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MemberChatRoom> memberChatRooms = new ArrayList<>();

    public static ChatRoom createChatRoom(String name, Member createdBy) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .createdBy(createdBy)
                .build();

        MemberChatRoom.userJoinsChatRoom(createdBy, chatRoom);

        return chatRoom;
    }

    public void addUserChatRoom(MemberChatRoom memberChatRoom) {
        memberChatRooms.add(memberChatRoom);
        memberChatRoom.setChatRoom(this);
    }
}
