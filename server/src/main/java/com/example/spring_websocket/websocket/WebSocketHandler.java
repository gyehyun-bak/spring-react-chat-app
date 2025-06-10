package com.example.spring_websocket.websocket;

import com.example.spring_websocket.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {

    private final MemberService memberService;

    /**
     * STOMP 서브 프로토콜을 사용하는 웹소켓 세션의 연결이 끊기면 발생하는 이벤트 {@link SessionDisconnectEvent}를 처리합니다.
     */
    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());

        if (accessor.getSessionAttributes() == null || !accessor.getSessionAttributes().containsKey("memberId")) {
            throw new IllegalStateException("No SessionAttributes or memberId. sessionId: " + accessor.getSessionId());
        }

        Long memberId = Long.parseLong((String) accessor.getSessionAttributes().get("memberId"));

        memberService.leave(memberId);
    }
}
