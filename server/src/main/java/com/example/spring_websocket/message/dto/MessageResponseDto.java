package com.example.spring_websocket.message.dto;

import com.example.spring_websocket.message.domain.Message;
import com.example.spring_websocket.message.domain.MessageType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponseDto {
    private MessageType type;
    private String content;
    private Long memberId;
    private String nickname;

    public MessageResponseDto(Message message) {
        this.type = message.getType();
        this.content = message.getContent();
        this.memberId = message.getMember().getId();
        this.nickname = message.getMember().getNickname();
    }
}
