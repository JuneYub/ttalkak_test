package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.entity.news.NewsLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsLetterRepository extends JpaRepository<NewsLetter, Long> {
}
