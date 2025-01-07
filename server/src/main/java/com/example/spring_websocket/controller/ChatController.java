package com.example.spring_websocket.controller;

import com.example.spring_websocket.dto.request.MessageRequestDto;
import com.example.spring_websocket.dto.response.MessageResponseDto;
import com.example.spring_websocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public MessageResponseDto sendChatMessage(MessageRequestDto requestDto, SimpMessageHeaderAccessor accessor) {
        return chatService.processMessage(requestDto, accessor.getSessionId(), (String) accessor.getSessionAttributes().get("nickname"));
    }
}
