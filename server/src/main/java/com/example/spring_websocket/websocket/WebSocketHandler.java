package com.example.spring_websocket.websocket;

import com.example.spring_websocket.chatroom.ChatRoomService;
import com.example.spring_websocket.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {

    private final MemberService memberService;
    private final ChatRoomService chatRoomService;

    /**
     * STOMP 서브 프로토콜을 사용하는 웹소켓 세션의 연결이 끊기면 발생하는 이벤트 {@link SessionDisconnectEvent}를 처리합니다.
     */
    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Long memberId = getMemberIdFromHeaderAccessor(accessor);

        memberService.leave(memberId);
    }

    @EventListener
    public void handleSessionUnsubscribe(SessionUnsubscribeEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Long memberId = getMemberIdFromHeaderAccessor(accessor);
        Long chatRoomId = getChatRoomIdFromHeaderAccessor(accessor);
        chatRoomService.leave(memberId, chatRoomId);
    }

    private Long getMemberIdFromHeaderAccessor(SimpMessageHeaderAccessor accessor) {
        if (accessor.getSessionAttributes() == null || !accessor.getSessionAttributes().containsKey("memberId")) {
            throw new IllegalStateException("No SessionAttributes or memberId. sessionId: " + accessor.getSessionId());
        }

        return (Long) accessor.getSessionAttributes().get("memberId");
    }

    private Long getChatRoomIdFromHeaderAccessor(SimpMessageHeaderAccessor accessor) {
        if (accessor.getDestination() == null || !accessor.getDestination().startsWith("/topic/chat-rooms/")) {
            throw new IllegalStateException("No destination or destination is not start with '/topic/chat-rooms/'. destination: " + accessor.getDestination());
        }

        return Long.parseLong(accessor.getDestination().replace("/topic/chat-rooms/", ""));
    }
}
