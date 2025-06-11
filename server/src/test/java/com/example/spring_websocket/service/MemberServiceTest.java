package com.example.spring_websocket.service;

import com.example.spring_websocket.member.Member;
import com.example.spring_websocket.member.MemberService;
import com.example.spring_websocket.member.dto.response.JoinResponseDto;
import com.example.spring_websocket.chatroom.ChatRoomRepository;
import com.example.spring_websocket.memberchatroom.MemberChatRoomRepository;
import com.example.spring_websocket.member.MemberRepository;
import com.example.spring_websocket.global.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private MemberService memberService;

    @Test
    void join() {
        // given
        String nickname = "nickname";

        Member mockMember = Mockito.mock(Member.class);
        when(mockMember.getId()).thenReturn(1L);
        when(memberRepository.save(any(Member.class))).thenReturn(mockMember);
        when(jwtTokenProvider.createToken(any(String.class))).thenReturn("accessToken");

        // when
        ResponseEntity<JoinResponseDto> response = memberService.join(nickname);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
        assertThat(response.getBody().getAccessToken()).isNotNull();
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