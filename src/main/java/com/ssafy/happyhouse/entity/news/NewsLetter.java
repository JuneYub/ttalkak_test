package com.ssafy.happyhouse.entity.news;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "newsletter")
public class NewsLetter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipients;
    private String title;
    private String content;
    private String sendDate;
    private int isSend;

    public void updateIsSendStatus() {
        this.isSend = 1;
    }
}
