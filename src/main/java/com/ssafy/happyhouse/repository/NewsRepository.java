package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.entity.news.News;
import com.ssafy.happyhouse.entity.news.NewsLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News n WHERE n.newsDate = :today")
    List<News> findRelatedNews(@Param("today") LocalDate today);

}
