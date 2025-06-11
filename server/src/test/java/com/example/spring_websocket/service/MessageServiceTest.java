package com.example.spring_websocket.service;

import com.example.spring_websocket.chatroom.ChatRoom;
import com.example.spring_websocket.member.Member;
import com.example.spring_websocket.message.MessageService;
import com.example.spring_websocket.message.domain.Message;
import com.example.spring_websocket.message.domain.MessageType;
import com.example.spring_websocket.message.dto.MessageResponseDto;
import com.example.spring_websocket.chatroom.ChatRoomRepository;
import com.example.spring_websocket.member.MemberRepository;
import com.example.spring_websocket.message.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private MessageService messageService;

    @Test
    void sendChatMessage() {
        // given
        Long memberId = 1L;
        Long chatRoomId = 2L;
        String content = "Hello World!";
        Member member = Member.createMember("member");
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(ChatRoom.createChatRoom("chatRoom", member)));

        // when
        messageService.sendChatMessage(memberId, chatRoomId, content);

        // then
        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(captor.capture());

        Message savedMessage = captor.getValue();
        assertThat(savedMessage.getContent()).isEqualTo(content);
        assertThat(savedMessage.getType()).isEqualTo(MessageType.CHAT);
        verify(simpMessagingTemplate, times(1)).convertAndSend(any(), any(MessageResponseDto.class));
    }

    @Test
    void sendSystemMessage() {
        // given
        Long memberId = 1L;
        Long chatRoomId = 2L;
        String content = "Hello World!";
        Member member = Member.createMember("member");
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(chatRoomRepository.findById(chatRoomId)).thenReturn(Optional.of(ChatRoom.createChatRoom("chatRoom", member)));

        // when
        messageService.sendSystemMessage(memberId, chatRoomId, content);

        // then
        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(captor.capture());

        Message savedMessage = captor.getValue();
        assertThat(savedMessage.getContent()).isEqualTo(content);
        assertThat(savedMessage.getType()).isEqualTo(MessageType.SYSTEM);
        verify(simpMessagingTemplate, times(1)).convertAndSend(any(), any(MessageResponseDto.class));
    }

    @Test
    void sendJoinedMessage() {
        // given
        Member member = Member.createMember("member");
        ChatRoom chatRoom = ChatRoom.createChatRoom("chatRoom", member);

        // when
        messageService.sendJoinedMessage(member, chatRoom);

        // then
        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(captor.capture());
        Message savedMessage = captor.getValue();
        assertThat(savedMessage.getType()).isEqualTo(MessageType.SYSTEM);
        verify(simpMessagingTemplate, times(1)).convertAndSend(any(), any(MessageResponseDto.class));
    }

    @Test
    void sendLeftMessage() {
        // given
        Member member = Member.createMember("member");
        ChatRoom chatRoom = ChatRoom.createChatRoom("chatRoom", member);

        // when
        messageService.sendLeftMessage(member, chatRoom);

        // then
        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(captor.capture());
        Message savedMessage = captor.getValue();
        assertThat(savedMessage.getType()).isEqualTo(MessageType.SYSTEM);
        verify(simpMessagingTemplate, times(1)).convertAndSend(any(), any(MessageResponseDto.class));
    }
}