package com.example.spring_websocket.service;

import com.example.spring_websocket.domain.Member;
import com.example.spring_websocket.repository.ChatRoomRepository;
import com.example.spring_websocket.repository.MemberChatRoomRepository;
import com.example.spring_websocket.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private MemberChatRoomRepository memberChatRoomRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void join() {
        // given
        String nickname = "nickname";

        // when
        memberService.join(nickname);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void leave() {
        // given
        Long memberId = 1L;
        Member member = Member.createMember("member");
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        memberService.leave(memberId);

        // then
        verify(memberChatRoomRepository, times(1)).deleteAllByMember(any(Member.class));
        verify(chatRoomRepository, times(1)).deleteAllEmptyChatRooms();
        verify(memberRepository, times(1)).delete(any(Member.class));
    }
}