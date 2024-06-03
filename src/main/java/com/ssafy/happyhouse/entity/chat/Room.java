package com.ssafy.happyhouse.entity.chat;

import com.ssafy.happyhouse.entity.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor
@Entity @Table(name = "room")
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Status status;

    @Column(name = "entired_time")
    private LocalDateTime entiredTime;

    @ManyToOne
    @JoinColumn(name = "expert_id")
    private Member expert;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Room(Status status, LocalDateTime entiredTime, Member expert, Member member) {
        this.status = status;
        this.entiredTime = entiredTime;
        this.expert = expert;
        this.member = member;
    }
}

/*
create table room (
    room_id bigint primary key auto_increment,
    status varchar(20),
    entired_time datetime(3),
    expert_id bigint,
    member_id bigint,
    foreign key (expert_id) references members(member_id),
    foreign key (member_id) references members(member_id)
);
 */
