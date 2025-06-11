package com.example.spring_websocket.chatroom;

import com.example.spring_websocket.member.Member;
import com.example.spring_websocket.memberchatroom.MemberChatRoom;
import com.example.spring_websocket.chatroom.dto.response.ChatRoomResponseDto;
import com.example.spring_websocket.chatroom.dto.response.ChatRoomsResponseDto;
import com.example.spring_websocket.memberchatroom.MemberChatRoomRepository;
import com.example.spring_websocket.member.MemberRepository;
import com.example.spring_websocket.message.MessageRepository;
import com.example.spring_websocket.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;
    private final MessageService messageService;
    private final MessageRepository messageRepository;

    @Transactional
    public ResponseEntity<ChatRoomResponseDto> createChatRoom(Long memberId, String name) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        ChatRoom chatRoom = ChatRoom.createChatRoom(name, member);

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        return ResponseEntity.ok(new ChatRoomResponseDto(savedChatRoom));
    }

    public ResponseEntity<ChatRoomsResponseDto> findAll() {
        return ResponseEntity.ok(new ChatRoomsResponseDto(chatRoomRepository.findAll()));
    }

    @Transactional
    public void join(long memberId, long chatRoomId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        if (memberChatRoomRepository.existsMemberChatRoomByMemberAndChatRoom(member, chatRoom)) {
            return;
        }

        MemberChatRoom memberChatRoom = MemberChatRoom.memberJoinsChatRoom(member, chatRoom);

        messageService.sendJoinedMessage(member, chatRoom);

        memberChatRoomRepository.save(memberChatRoom);
    }

    @Transactional
    public void leave(long memberId, long chatRoomId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();

        memberChatRoomRepository.deleteByMemberAndChatRoom(member, chatRoom);
        if (deleteChatRoomIfEmpty(chatRoom)) return;

        messageService.sendLeftMessage(member, chatRoom);
    }

    private boolean deleteChatRoomIfEmpty(ChatRoom chatRoom) {
        if (memberChatRoomRepository.countMemberChatRoomByChatRoom(chatRoom) == 0L) {
            messageRepository.deleteByChatRoom(chatRoom);
            chatRoomRepository.delete(chatRoom);
            return true;
        }
        return false;
    }
}
