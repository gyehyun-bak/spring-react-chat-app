package com.example.spring_websocket.service;

import com.example.spring_websocket.domain.ChatRoom;
import com.example.spring_websocket.domain.Member;
import com.example.spring_websocket.repository.ChatRoomRepository;
import com.example.spring_websocket.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private ChatRoomService chatRoomService;

    @Test
    void createChatRoom() {
        // given
        Long memberId = 1L;
        Member member = Member.createMember("member");
        String name = "name";
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        chatRoomService.createChatRoom(memberId, name);

        // then
        verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
    }
}