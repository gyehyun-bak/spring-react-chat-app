package com.example.spring_websocket.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void createUser() {
        // given
        String nickname = "nickname";

        // when
        User user = User.createUser(nickname);

        // then
        Assertions.assertThat(user.getNickname()).isEqualTo(nickname);
    }
}