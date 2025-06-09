package com.example.spring_websocket.service;

import com.example.spring_websocket.domain.ChatRoom;
import com.example.spring_websocket.domain.Member;
import com.example.spring_websocket.domain.MemberChatRoom;
import com.example.spring_websocket.repository.ChatRoomRepository;
import com.example.spring_websocket.repository.MemberChatRoomRepository;
import com.example.spring_websocket.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberChatRoomServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberChatRoomRepository memberChatRoomRepository;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MemberChatRoomService memberChatRoomService;

    @Test
    void joinChatRoom() {
        // given
        ChatRoom chatRoom = ChatRoom.createChatRoom("chatRoom", Member.createMember("member"));
        long chatRoomId = 1L;

        Member guest = Member.createMember("guest");
        long guestId = 2L;

        when(memberRepository.findById(guestId)).thenReturn(Optional.of(guest));
        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(chatRoom));

        // when
        memberChatRoomService.join(guestId, chatRoomId);

        // then
        ArgumentCaptor<MemberChatRoom> captor = ArgumentCaptor.forClass(MemberChatRoom.class);
        verify(memberChatRoomRepository).save(captor.capture());

        MemberChatRoom memberChatRoom = captor.getValue();
        assertThat(memberChatRoom.getMember()).isEqualTo(guest);
        assertThat(memberChatRoom.getChatRoom()).isEqualTo(chatRoom);
        verify(messageService, times(1)).sendJoinedMessage(guest, chatRoom);
    }

    @Test
    void leaveChatRoom() {
        // given
        ChatRoom chatRoom = ChatRoom.createChatRoom("chatRoom", Member.createMember("member"));
        long chatRoomId = 1L;

        Member guest = Member.createMember("guest");
        long guestId = 2L;

        when(memberRepository.findById(guestId)).thenReturn(Optional.of(guest));
        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(chatRoom));

        // when
        memberChatRoomService.leave(guestId, chatRoomId);

        // then
        verify(memberChatRoomRepository, times(1)).deleteByMemberAndChatRoom(guest, chatRoom);
        verify(messageService, times(1)).sendLeftMessage(guest, chatRoom);
    }
}