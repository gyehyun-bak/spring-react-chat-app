package com.example.spring_websocket.member;

import com.example.spring_websocket.member.dto.request.JoinRequestDto;
import com.example.spring_websocket.member.dto.request.MemberMeRequestDto;
import com.example.spring_websocket.member.dto.response.JoinResponseDto;
import com.example.spring_websocket.member.dto.response.MemberResponseDto;
import com.example.spring_websocket.member.dto.response.MembersResponseDto;
import com.example.spring_websocket.global.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    public ResponseEntity<JoinResponseDto> join(@RequestBody JoinRequestDto requestDto) {
        return memberService.join(requestDto.getNickname());
    }

    @GetMapping
    public ResponseEntity<MembersResponseDto> members() {
        return memberService.findAll();
    }

    @PostMapping("/me")
    public ResponseEntity<MemberResponseDto> member(@RequestBody MemberMeRequestDto requestDto) {
        Long memberId = jwtTokenProvider.getMemberIdFromToken(requestDto.getAccessToken());
        return memberService.findById(memberId);
    }
}
