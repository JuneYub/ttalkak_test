package com.ssafy.happyhouse.entity.chat;

import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Chat {

    private String message; // 메시지 내용
    private String sender; // 발신자
    private String timestamp;
}
