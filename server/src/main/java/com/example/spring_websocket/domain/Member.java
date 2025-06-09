package com.example.spring_websocket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;

    public static Member createMember(String nickname) {
        return Member.builder().nickname(nickname).build();
    }
}
