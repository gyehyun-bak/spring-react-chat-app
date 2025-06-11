package com.example.spring_websocket.message.domain;

import com.example.spring_websocket.chatroom.ChatRoom;
import com.example.spring_websocket.global.audit.BaseEntity;
import com.example.spring_websocket.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Message extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Message createChatMessage(Member member, ChatRoom chatRoom, String content) {
        return Message.builder()
                .content(content)
                .type(MessageType.CHAT)
                .member(member)
                .chatRoom(chatRoom)
                .build();
    }

    public static Message createSystemMessage(Member member, ChatRoom chatRoom, String content) {
        return Message.builder()
                .content(content)
                .type(MessageType.SYSTEM)
                .member(member)
                .chatRoom(chatRoom)
                .build();
    }
}
