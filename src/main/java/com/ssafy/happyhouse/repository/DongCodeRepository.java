package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.entity.dongcode.DongCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DongCodeRepository extends JpaRepository<DongCode, String> {

    @Query("SELECT d.dongCode FROM DongCode d WHERE d.gugunName = :gugunName AND d.dongName = :dongName")
    String findDongCodeByDongName(@Param("gugunName")String gugunName, @Param("dongName") String dongName);
}
