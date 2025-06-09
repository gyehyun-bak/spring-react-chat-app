package com.example.spring_websocket.service;

import com.example.spring_websocket.domain.ChatRoom;
import com.example.spring_websocket.domain.Member;
import com.example.spring_websocket.domain.MemberChatRoom;
import com.example.spring_websocket.domain.Message;
import com.example.spring_websocket.dto.response.JoinResponseDto;
import com.example.spring_websocket.repository.ChatRoomRepository;
import com.example.spring_websocket.repository.MessageRepository;
import com.example.spring_websocket.repository.MemberChatRoomRepository;
import com.example.spring_websocket.repository.MemberRepository;
import com.example.spring_websocket.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;
    private final MessageService messageService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseEntity<JoinResponseDto> join(String nickname) {
        Member member = Member.createMember(nickname);

        Member savedMember = memberRepository.save(member);

        String accessToken = jwtTokenProvider.createToken(String.valueOf(savedMember.getId()));

        return ResponseEntity.ok(new JoinResponseDto(accessToken));
    }

    @Transactional
    public void leave(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow();

        member.getMemberChatRooms().forEach(memberChatRoom -> {
            ChatRoom chatRoom = memberChatRoom.getChatRoom();
            messageService.sendLeftMessage(member, chatRoom);
        });

        memberChatRoomRepository.deleteAllByMember(member);
        chatRoomRepository.deleteAllEmptyChatRooms();

        memberRepository.delete(member);
    }
}
