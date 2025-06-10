package com.example.spring_websocket.service;

import com.example.spring_websocket.domain.ChatRoom;
import com.example.spring_websocket.domain.Member;
import com.example.spring_websocket.domain.MemberChatRoom;
import com.example.spring_websocket.dto.response.ChatRoomResponseDto;
import com.example.spring_websocket.dto.response.ChatRoomsResponseDto;
import com.example.spring_websocket.repository.ChatRoomRepository;
import com.example.spring_websocket.repository.MemberChatRoomRepository;
import com.example.spring_websocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;
    private final MessageService messageService;


    @Transactional
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(Long memberId, String name) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        ChatRoom chatRoom = ChatRoom.createChatRoom(name, member);

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return ResponseEntity.ok(new ChatRoomResponseDto(savedChatRoom));
    }

    public ResponseEntity<ChatRoomsResponseDto> findAll() {
        return ResponseEntity.ok(new ChatRoomsResponseDto(chatRoomRepository.findAll()));
    }

    @Transactional
    public void join(long memberId, long chatRoomId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        MemberChatRoom memberChatRoom = MemberChatRoom.memberJoinsChatRoom(member, chatRoom);

        messageService.sendJoinedMessage(member, chatRoom);

        memberChatRoomRepository.save(memberChatRoom);
    }

    @Transactional
    public void leave(long memberId, long chatRoomId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        messageService.sendLeftMessage(member, chatRoom);

        memberChatRoomRepository.deleteByMemberAndChatRoom(member, chatRoom);
        chatRoomRepository.deleteAllEmptyChatRooms();
    }
}
