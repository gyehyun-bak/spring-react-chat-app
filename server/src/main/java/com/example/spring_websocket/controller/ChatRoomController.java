package com.example.spring_websocket.controller;

import com.example.spring_websocket.dto.request.ChatRoomCreateRequestDto;
import com.example.spring_websocket.dto.request.ChatRoomJoinRequestDto;
import com.example.spring_websocket.dto.request.ChatRoomLeaveRequestDto;
import com.example.spring_websocket.dto.response.ChatRoomResponseDto;
import com.example.spring_websocket.dto.response.ChatRoomsResponseDto;
import com.example.spring_websocket.service.ChatRoomService;
import com.example.spring_websocket.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<ChatRoomsResponseDto> chatRooms() {
        return chatRoomService.findAll();
    }

    @PostMapping
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomCreateRequestDto requestDto) {
        Long memberId = jwtTokenProvider.getMemberIdFromToken(requestDto.getAccessToken());
        return chatRoomService.createChatRoom(memberId, requestDto.getName());
    }

    @PostMapping("/{chatRoomId}/join")
    public ResponseEntity<?> joinChatRoom(@RequestBody ChatRoomJoinRequestDto requestDto, @PathVariable Long chatRoomId) {
        long memberId = jwtTokenProvider.getMemberIdFromToken(requestDto.getAccessToken());
        chatRoomService.join(memberId, chatRoomId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{chatRoomId}/leave")
    public ResponseEntity<?> leaveChatRoom(@RequestBody ChatRoomLeaveRequestDto requestDto, @PathVariable Long chatRoomId) {
        long memberId = jwtTokenProvider.getMemberIdFromToken(requestDto.getAccessToken());
        chatRoomService.leave(memberId, chatRoomId);
        return ResponseEntity.ok().build();
    }
}
