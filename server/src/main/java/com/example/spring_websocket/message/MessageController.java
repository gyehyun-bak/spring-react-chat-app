package com.example.spring_websocket.message;

import com.example.spring_websocket.message.dto.MessageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat-rooms/{chatRoomId}")
    public void sendChatMessage(MessageRequestDto requestDto, SimpMessageHeaderAccessor accessor, @DestinationVariable Long chatRoomId) {
        if (accessor.getSessionAttributes() == null || !accessor.getSessionAttributes().containsKey("memberId")) {
            throw new IllegalStateException("No SessionAttributes or memberId. sessionId: " + accessor.getSessionId());
        }

        Long memberId = (Long) accessor.getSessionAttributes().get("memberId");

        messageService.sendChatMessage(memberId, chatRoomId, requestDto.getContent());
    }
}
