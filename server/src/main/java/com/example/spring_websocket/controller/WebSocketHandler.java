package com.example.spring_websocket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {
    /**
     * STOMP 서브 프로토콜을 통한 CONNECT 메시지가 수신되면 발생하는 이벤트 {@link SessionConnectEvent}를 처리합니다.
     * <p> STOMP CONNECT 메시지 헤더에서 nickname을 추출해 웹소켓 세션 헤더에 저장합니다.
     */
    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String nickname = accessor.getFirstNativeHeader("nickname");

        // 세션에 닉네임 저장
        accessor.getSessionAttributes().put("nickname", nickname);

        // 로깅을 위해 sessionId와 함께 보여줍니다
        String sessionId = accessor.getSessionId();
        log.info("sessionId: " + sessionId + ", nickname: " + nickname);
    }
}
