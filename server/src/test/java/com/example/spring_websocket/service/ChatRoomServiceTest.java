package com.example.spring_websocket.service;

import com.example.spring_websocket.chatroom.ChatRoom;
import com.example.spring_websocket.chatroom.ChatRoomService;
import com.example.spring_websocket.member.Member;
import com.example.spring_websocket.memberchatroom.MemberChatRoom;
import com.example.spring_websocket.chatroom.dto.response.ChatRoomResponseDto;
import com.example.spring_websocket.chatroom.dto.response.ChatRoomsResponseDto;
import com.example.spring_websocket.chatroom.ChatRoomRepository;
import com.example.spring_websocket.memberchatroom.MemberChatRoomRepository;
import com.example.spring_websocket.member.MemberRepository;
import com.example.spring_websocket.message.MessageRepository;
import com.example.spring_websocket.message.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberChatRoomRepository memberChatRoomRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageService messageService;
    @InjectMocks
    private ChatRoomService chatRoomService;

    @Test
    void createChatRoom() {
        // given
        Long memberId = 1L;
        Long chatRoomId = 2L;
        String name = "name";

        Member mockMember = Mockito.mock(Member.class);
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(mockMember));

        ChatRoom mockChatRoom = Mockito.mock(ChatRoom.class);
        when(mockChatRoom.getId()).thenReturn(chatRoomId);
        when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(mockChatRoom);

        // when
        ResponseEntity<ChatRoomResponseDto> response = chatRoomService.createChatRoom(memberId, name);

        // then
        assertThat(response.getBody().getId()).isEqualTo(chatRoomId);
        verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
    }

    @Test
    void findAll() {
        // given
        when(chatRoomRepository.findAll()).thenReturn(List.of(ChatRoom.createChatRoom("chatRoom", Member.createMember("member"))));

        // when
        ResponseEntity<ChatRoomsResponseDto> response = chatRoomService.findAll();

        // then
        assertThat(response.getBody().getChatRooms().size()).isEqualTo(1);
        verify(chatRoomRepository, times(1)).findAll();
    }

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
        chatRoomService.join(guestId, chatRoomId);

        // then
        ArgumentCaptor<MemberChatRoom> captor = ArgumentCaptor.forClass(MemberChatRoom.class);
        verify(memberChatRoomRepository).save(captor.capture());

        MemberChatRoom memberChatRoom = captor.getValue();
        assertThat(memberChatRoom.getMember()).isEqualTo(guest);
        assertThat(memberChatRoom.getChatRoom()).isEqualTo(chatRoom);
        verify(messageService, times(1)).sendJoinedMessage(guest, chatRoom);
    }

    @Test
    void joinChatRoomIgnoreIfAlreadyJoined() {
        // given
        ChatRoom chatRoom = ChatRoom.createChatRoom("chatRoom", Member.createMember("member"));
        long chatRoomId = 1L;

        Member guest = Member.createMember("guest");
        long guestId = 2L;

        when(memberRepository.findById(guestId)).thenReturn(Optional.of(guest));
        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(chatRoom));
        when(memberChatRoomRepository.existsMemberChatRoomByMemberAndChatRoom(guest, chatRoom)).thenReturn(true);

        // when
        chatRoomService.join(guestId, chatRoomId);

        // then
        verify(memberChatRoomRepository, never()).save(any(MemberChatRoom.class));
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
        chatRoomService.leave(guestId, chatRoomId);

        // then
        verify(memberChatRoomRepository, times(1)).deleteByMemberAndChatRoom(guest, chatRoom);
    }
}