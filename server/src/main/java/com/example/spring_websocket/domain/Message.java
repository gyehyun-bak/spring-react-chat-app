package com.example.spring_websocket.domain;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    public static Message createMessage(User user, ChatRoom chatRoom, String content) {
        return Message.builder()
                .createdBy(user)
                .chatRoom(chatRoom)
                .content(content)
                .build();
    }
}
