package com.example.spring_websocket.service;

import com.example.spring_websocket.dto.request.MessageRequestDto;
import com.example.spring_websocket.dto.response.MessageResponseDto;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public MessageResponseDto processMessage(MessageRequestDto requestDto) {
        return new MessageResponseDto(requestDto.getContent());
    }
}
