package com.example.spring_websocket.service.impl;

import com.example.spring_websocket.dto.request.MessageRequestDto;
import com.example.spring_websocket.dto.response.MessageResponseDto;
import com.example.spring_websocket.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    public MessageResponseDto processMessage(MessageRequestDto requestDto) {
        return new MessageResponseDto(requestDto.getContent());
    }
}
