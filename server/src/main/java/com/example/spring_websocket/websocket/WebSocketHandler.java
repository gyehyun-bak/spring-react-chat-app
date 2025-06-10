package com.example.spring_websocket.websocket;

import com.example.spring_websocket.domain.enums.MessageType;
import com.example.spring_websocket.dto.response.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {
    /**
     * 메시지를 클래스 내에서 직접 SimpleBroker로 발행하는 데 사용됩니다.
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * STOMP 서브 프로토콜을 통한 CONNECT 메시지가 수신되면 발생하는 이벤트 {@link SessionConnectEvent}를 처리합니다.
     * <ul>
     *     <li>STOMP CONNECT 메시지 헤더에서 nickname을 추출해 웹소켓 세션 헤더에 저장합니다.</li>
     *     <li>사용자가 연결되었음을 알리는 메시지를 발행합니다.</li>
     * </ul>
     */
    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String nickname = accessor.getFirstNativeHeader("nickname");

        // 세션에 닉네임 저장
        accessor.getSessionAttributes().put("nickname", nickname);

        // 디버깅을 위해 sessionId와 nickname 을 함께 보여줍니다.
        String sessionId = accessor.getSessionId();
        log.info("[SessionConnected]: sessionId = " + sessionId + ", nickname = " + nickname);

        // 시스템 메시지를 생성하고 발행합니다.
        MessageResponseDto responseDto = MessageResponseDto.builder()
                .type(MessageType.SYSTEM)
                .content(nickname + "님이 참가하였습니다.")
                .build();

        messagingTemplate.convertAndSend("/topic/chat", responseDto);
    }

    /**
     * STOMP 서브 프로토콜을 사용하는 웹소켓 세션의 연결이 끊기면 발생하는 이벤트 {@link SessionDisconnectEvent}를 처리합니다.
     * <ul>
     *     <li>사용자가 연결 해제되었음을 알리는 메시지를 발행합니다.</li>
     * </ul>
     */
    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String nickname = (String) accessor.getSessionAttributes().get("nickname");

        // 디버깅을 위해 sessionId와 nickname 을 함께 보여줍니다.
        String sessionId = accessor.getSessionId();
        log.info("[SessionDisconnected]: sessionId = " + sessionId + ", nickname = " + nickname);

        // 시스템 메시지를 생성하고 발행합니다.
        MessageResponseDto responseDto = MessageResponseDto.builder()
                .type(MessageType.SYSTEM)
                .content(nickname + "님이 나갔습니다.")
                .build();

        messagingTemplate.convertAndSend("/topic/chat", responseDto);
    }
}
