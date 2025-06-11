package com.example.spring_websocket.member.dto.response;

import com.example.spring_websocket.member.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponseDto {
    private Long id;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.createdAt = member.getCreatedAt();
        this.lastModifiedAt = member.getLastModifiedAt();
    }
}
