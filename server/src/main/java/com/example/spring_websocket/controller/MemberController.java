package com.example.spring_websocket.controller;

import com.example.spring_websocket.dto.request.JoinRequestDto;
import com.example.spring_websocket.dto.response.JoinResponseDto;
import com.example.spring_websocket.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<JoinResponseDto> join(@RequestBody JoinRequestDto requestDto) {
        return memberService.join(requestDto.getNickname());
    }
}
