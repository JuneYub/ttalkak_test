package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.entity.news.NewsLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsLetterRepository extends JpaRepository<NewsLetter, Long> {

    @Query("SELECT n FROM NewsLetter n WHERE n.isSend = 0")
    List<NewsLetter> findNewslettersNotSent();

}
