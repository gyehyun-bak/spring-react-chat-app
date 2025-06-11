package com.example.spring_websocket.websocket;

import com.example.spring_websocket.global.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;


@RequiredArgsConstructor
public class MyChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        if (StompCommand.CONNECT.equals(command)) {
            handleConnect(accessor);
        }

        return message;
    }

    private void handleConnect(StompHeaderAccessor accessor) {
        if (accessor == null || accessor.getSessionAttributes() == null) {
            throw new IllegalStateException("No StompHeaderAccessor");
        }

        String accessToken = accessor.getFirstNativeHeader("accessToken");
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalStateException("No accessToken");
        }

        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new IllegalStateException("Invalid accessToken");
        }

        Long memberId = jwtTokenProvider.getMemberIdFromToken(accessToken);
        accessor.getSessionAttributes().put("memberId", memberId);
    }
}
