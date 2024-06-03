package com.ssafy.happyhouse.dto.room;

import com.ssafy.happyhouse.entity.chat.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class RoomDto {

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Detail {

        private Long roomId;

        private Status status;

        private LocalDateTime entiredTime;

        private Long memberId;

        private Long expertId;

        private String expertNickname;
    }
}
