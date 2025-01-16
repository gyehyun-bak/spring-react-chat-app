package com.example.spring_websocket.common.enums;

import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * UI 구분을 위한 메시지 타입입니다.
 * <ul>
 *     <li>SYSTEM: 특정 사용자가 아닌 시스템에서 발행한 메시지입니다.</li>
 *     <li>CHAT: 사용자가 채팅을 목적으로 발행한 메시지입니다.</li>
 * </ul>
 */
public enum MessageType {
    SYSTEM,
    CHAT
}
