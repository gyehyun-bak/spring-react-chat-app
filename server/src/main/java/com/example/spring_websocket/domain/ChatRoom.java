package com.example.spring_websocket.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private User createdBy;

    public static ChatRoom createChatRoom(String name, User createdBy) {
        return ChatRoom.builder()
                .name(name)
                .createdBy(createdBy)
                .build();
    }
}
