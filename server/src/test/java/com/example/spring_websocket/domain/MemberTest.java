package com.example.spring_websocket.domain;

import com.example.spring_websocket.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void createMember() {
        // given
        String nickname = "nickname";

        // when
        Member member = Member.createMember(nickname);

        // then
        Assertions.assertThat(member.getNickname()).isEqualTo(nickname);
    }
}