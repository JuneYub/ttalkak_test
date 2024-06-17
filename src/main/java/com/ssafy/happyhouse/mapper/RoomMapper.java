package com.ssafy.happyhouse.mapper;

import java.util.List;
import java.util.Map;

public interface RoomMapper {

    Map<String, Object> findById(Long roomId);

    List<Map<String, Object>> findByMemberId(Long memberId);

    List<Map<String, Object>> findByExpertId(Long expertId);

    void createRoom(Map<String, Object> map);

    void deleteRoom(Long roomId);
}
