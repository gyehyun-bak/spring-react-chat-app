package com.example.spring_websocket.controller;

import com.example.spring_websocket.dto.request.MessageRequestDto;
import com.example.spring_websocket.dto.response.MessageResponseDto;
import com.example.spring_websocket.service.ChatService;
import com.example.spring_websocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;
    private final MessageService messageService;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public MessageResponseDto sendChatMessage(MessageRequestDto requestDto, SimpMessageHeaderAccessor accessor) {
        return chatService.processMessage(requestDto, accessor.getSessionId(), (String) accessor.getSessionAttributes().get("nickname"));
    }

    @MessageMapping("/chat-rooms/{chatRoomId}")
    public void sendChatMessage(MessageRequestDto requestDto, SimpMessageHeaderAccessor accessor, @DestinationVariable Long chatRoomId) {
        if (accessor.getSessionAttributes() == null || !accessor.getSessionAttributes().containsKey("memberId")) {
            throw new IllegalStateException("No SessionAttributes or memberId. sessionId: " + accessor.getSessionId());
        }

        Long memberId = (Long) accessor.getSessionAttributes().get("memberId");

        messageService.sendChatMessage(memberId, chatRoomId, requestDto.getContent());
    }
}
