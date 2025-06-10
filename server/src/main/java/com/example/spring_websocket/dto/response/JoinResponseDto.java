package com.example.spring_websocket.dto.response;

import com.example.spring_websocket.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinResponseDto {
    String accessToken;
    Long memberId;

    public JoinResponseDto(String accessToken, Member member) {
        this.accessToken = accessToken;
        this.memberId = member.getId();
    }
}
