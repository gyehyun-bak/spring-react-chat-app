package com.example.spring_websocket.service;

import com.example.spring_websocket.domain.ChatRoom;
import com.example.spring_websocket.domain.Member;
import com.example.spring_websocket.repository.ChatRoomRepository;
import com.example.spring_websocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void createChatRoom(Long memberId, String name) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        ChatRoom chatRoom = ChatRoom.createChatRoom(name, member);

        chatRoomRepository.save(chatRoom);
    }
}
