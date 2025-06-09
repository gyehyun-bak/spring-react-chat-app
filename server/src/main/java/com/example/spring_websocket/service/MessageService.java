package com.example.spring_websocket.service;

import com.example.spring_websocket.domain.ChatRoom;
import com.example.spring_websocket.domain.Member;
import com.example.spring_websocket.domain.Message;
import com.example.spring_websocket.dto.response.MessageResponseDto;
import com.example.spring_websocket.repository.ChatRoomRepository;
import com.example.spring_websocket.repository.MemberRepository;
import com.example.spring_websocket.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @Transactional
    public void sendChatMessage(Long memberId, Long chatRoomId, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        Message chatMessage = Message.createChatMessage(member, chatRoom, content);

        publishMessage(chatMessage);
        messageRepository.save(chatMessage);
    }

    @Transactional
    public void sendSystemMessage(Long memberId, Long chatRoomId, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        Message chatMessage = Message.createSystemMessage(member, chatRoom, content);

        publishMessage(chatMessage);
        messageRepository.save(chatMessage);
    }

    private void publishMessage(Message message) {
        simpMessagingTemplate.convertAndSend("/topic/chat" + message.getChatRoom().getId(), new MessageResponseDto(message));
    }
}
