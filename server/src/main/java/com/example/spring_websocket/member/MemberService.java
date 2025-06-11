package com.example.spring_websocket.member;

import com.example.spring_websocket.chatroom.ChatRoom;
import com.example.spring_websocket.member.dto.response.JoinResponseDto;
import com.example.spring_websocket.member.dto.response.MemberResponseDto;
import com.example.spring_websocket.member.dto.response.MembersResponseDto;
import com.example.spring_websocket.chatroom.ChatRoomRepository;
import com.example.spring_websocket.memberchatroom.MemberChatRoomRepository;
import com.example.spring_websocket.message.MessageService;
import com.example.spring_websocket.global.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return ResponseEntity.ok(new JoinResponseDto(accessToken, member));
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

    public ResponseEntity<MembersResponseDto> findAll() {
        return ResponseEntity.ok(new MembersResponseDto(memberRepository.findAll()));
    }

    public ResponseEntity<MemberResponseDto> findById(Long memberId) {
        return ResponseEntity.ok(new MemberResponseDto(memberRepository.findById(memberId).orElseThrow()));
    }
}
