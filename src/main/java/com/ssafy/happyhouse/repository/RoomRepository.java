package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.dto.room.RoomDto;
import com.ssafy.happyhouse.entity.chat.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {


    @Query("SELECT new com.ssafy.happyhouse.dto.room.RoomDto$Detail(r.id, r.status, r.entiredTime, r.member.id, r.expert.id, r.expert.nickname) " +
            "FROM Room r JOIN r.expert e " +
            "WHERE r.member.id = :memberId")
    List<RoomDto.Detail> findAllByMemberId(Long memberId);

    @Query("SELECT new com.ssafy.happyhouse.dto.room.RoomDto$Detail(r.id, r.status, r.entiredTime, r.member.id, r.expert.id, r.expert.nickname) " +
            "FROM Room r JOIN r.expert e " +
            "WHERE r.expert.id = :expertId")
    List<RoomDto.Detail> findAllByExpertId(Long expertId);



}
