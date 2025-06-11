package com.example.spring_websocket.global.audit;

import com.example.spring_websocket.member.Member;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Member> {

    @Override
    public Optional<Member> getCurrentAuditor() {
        return Optional.empty();
    }
}
