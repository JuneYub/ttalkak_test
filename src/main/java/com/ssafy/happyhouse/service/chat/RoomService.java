package com.ssafy.happyhouse.service.chat;

import com.ssafy.happyhouse.dto.room.RoomDto;
import com.ssafy.happyhouse.entity.chat.Room;
import com.ssafy.happyhouse.entity.chat.Status;
import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.EntityNotFoundException;
import com.ssafy.happyhouse.mapper.RoomMapper;
import com.ssafy.happyhouse.repository.MemberRepository;
import com.ssafy.happyhouse.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;


    public Room findById(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow((() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_ROOM)));

        return room;
    }

    public List<RoomDto.Detail> findByMemberId(Long memberId) {

        List<RoomDto.Detail> rooms = roomRepository.findAllByMemberId(memberId);
        return rooms;
    }

    public List<RoomDto.Detail> findByExpertId(Long expertId) {

        List<RoomDto.Detail> rooms = roomRepository.findAllByExpertId(expertId);
        return rooms;
    }

    @Transactional
    public void createRoom(Long expertId, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        Member expert = memberRepository.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        Room room = Room.builder()
                .status(Status.LIVE)
                .entiredTime(LocalDateTime.now().plusMinutes(30))
                .expert(expert)
                .member(member)
                .build();

        roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_ROOM));

        roomRepository.delete(room);
    }
}
