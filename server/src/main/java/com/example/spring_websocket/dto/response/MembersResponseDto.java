package com.example.spring_websocket.dto.response;

import com.example.spring_websocket.domain.Member;
import lombok.Data;

import java.util.List;

@Data
public class MembersResponseDto {
    private List<MemberResponseDto> members;

    public MembersResponseDto(List<Member> members) {
        this.members = members.stream()
                .map(MemberResponseDto::new)
                .toList();
    }
}
