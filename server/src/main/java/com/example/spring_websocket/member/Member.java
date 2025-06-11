package com.example.spring_websocket.member;

import com.example.spring_websocket.global.audit.BaseEntity;
import com.example.spring_websocket.memberchatroom.MemberChatRoom;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<MemberChatRoom> memberChatRooms =  new ArrayList<>();

    public static Member createMember(String nickname) {
        return Member.builder().nickname(nickname).build();
    }

    public void addMemberChatRoom(MemberChatRoom memberChatRoom) {
        memberChatRooms.add(memberChatRoom);
        memberChatRoom.setMember(this);
    }
}
