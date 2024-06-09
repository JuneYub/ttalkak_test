package com.ssafy.happyhouse.entity.news;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "news")
public class News {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate newsDate;
    private String newsTitle;
    private String newsLink;
    private String newsContent;
}
