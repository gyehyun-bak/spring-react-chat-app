package com.example.spring_websocket.websocket;

import com.example.spring_websocket.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof HttpServletRequest httpServletRequest) {
            Optional<String> accessTokenOpt = getAccessToken(httpServletRequest);

            if (accessTokenOpt.isEmpty()) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }

            String accessToken = accessTokenOpt.get();

            if (!jwtTokenProvider.validateToken(accessToken)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            };

            attributes.put("memberId", jwtTokenProvider.getMemberIdFromToken(accessToken));
            return true;
        }

        return false;
    }

    private Optional<String> getAccessToken(HttpServletRequest httpServletRequest) {
        String accessToken = httpServletRequest.getHeader("Authorization");

        if (accessToken == null || accessToken.isBlank()) {
            return Optional.empty();
        }

        accessToken = accessToken.replace("Bearer ", "");
        return Optional.of(accessToken);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
