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

    public static final String CHAT_DESTINATION_PREFIX = "/topic/chat/";
    public static final String HAS_JOINED = "님이 참가하였습니다.";
    public static final String HAS_LEFT = "님이 나갔습니다.";

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    public void sendChatMessage(Long memberId, Long chatRoomId, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        Message chatMessage = Message.createChatMessage(member, chatRoom, content);

        publish(chatMessage);
    }

    @Transactional
    public void sendSystemMessage(Long memberId, Long chatRoomId, String content) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        Message systemMessage = Message.createSystemMessage(member, chatRoom, content);

        publish(systemMessage);
    }

    @Transactional
    public void publish(Message message) {
        simpMessagingTemplate.convertAndSend(CHAT_DESTINATION_PREFIX + message.getChatRoom().getId(), new MessageResponseDto(message));
        messageRepository.save(message);
    }

    @Transactional
    public void sendJoinedMessage(Member member, ChatRoom chatRoom) {
        Message joinMessage = Message.createSystemMessage(member, chatRoom, member.getNickname() + HAS_JOINED);
        publish(joinMessage);
    }

    @Transactional
    public void sendLeftMessage(Member member, ChatRoom chatRoom) {
        Message leftMessage = Message.createSystemMessage(member, chatRoom, member.getNickname() + HAS_LEFT);
        publish(leftMessage);
    }
}
