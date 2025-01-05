package com.example.spring_websocket.service;

import com.example.spring_websocket.dto.request.MessageRequestDto;
import com.example.spring_websocket.dto.response.MessageResponseDto;

public interface ChatService {
    MessageResponseDto processMessage(MessageRequestDto requestDto, String sessionId);
}
