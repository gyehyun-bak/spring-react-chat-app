package com.example.spring_websocket.service.impl;

import com.example.spring_websocket.common.enums.MessageType;
import com.example.spring_websocket.dto.request.MessageRequestDto;
import com.example.spring_websocket.dto.response.MessageResponseDto;
import com.example.spring_websocket.service.ChatService;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    public MessageResponseDto processMessage(MessageRequestDto requestDto, String sessionId, String nickname) {
        return MessageResponseDto.builder()
                .type(MessageType.CHAT)
                .content(requestDto.getContent())
                .sessionId(sessionId)
                .nickname(nickname)
                .build();
    }
}
