package com.example.spring_websocket.service;

import com.example.spring_websocket.domain.Member;
import com.example.spring_websocket.domain.MemberChatRoom;
import com.example.spring_websocket.repository.ChatRoomRepository;
import com.example.spring_websocket.repository.MessageRepository;
import com.example.spring_websocket.repository.MemberChatRoomRepository;
import com.example.spring_websocket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;

    @Transactional
    public void join(String nickname) {
        Member member = Member.createMember(nickname);

        // TODO: 참가 메시지 전송

        memberRepository.save(member);
    }

    @Transactional
    public void leave(Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow();

        memberChatRoomRepository.deleteAllByMember(member);
        chatRoomRepository.deleteAllEmptyChatRooms();

        // TODO: 나가기 메시지 전송

        memberRepository.delete(member);
    }
}
