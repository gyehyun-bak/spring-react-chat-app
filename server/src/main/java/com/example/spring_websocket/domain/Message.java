package com.example.spring_websocket.domain;

import com.example.spring_websocket.domain.enums.MessageType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Message {
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
