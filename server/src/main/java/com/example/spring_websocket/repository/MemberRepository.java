package com.example.spring_websocket.repository;

import com.example.spring_websocket.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
