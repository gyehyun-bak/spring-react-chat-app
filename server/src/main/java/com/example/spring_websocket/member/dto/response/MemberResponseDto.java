package com.example.spring_websocket.member.dto.response;

import com.example.spring_websocket.member.Member;
import lombok.Data;

@Data
public class MemberResponseDto {
    private Long id;
    private String nickname;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
    }
}
