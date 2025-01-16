package com.example.spring_websocket.dto.response;

import com.example.spring_websocket.common.enums.MessageType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponseDto {
    private MessageType type;
    private String content;
    private String sessionId;
    private String nickname;
}
