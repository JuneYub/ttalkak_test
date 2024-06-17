package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.dto.house.HouseDetailDto;
import com.ssafy.happyhouse.entity.house.HouseInfo;
import com.ssafy.happyhouse.response.MapGugunMarkerInfoProjection;
import com.ssafy.happyhouse.response.MapMarkerInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseRepository extends JpaRepository<HouseInfo, Long> {
    @Query("SELECT new com.ssafy.happyhouse.dto.house.HouseDetailDto$ByDong(" +
            "a.aptCode," +
            "a.apartmentName," +
            "a.buildYear," +
            "REPLACE(b.dealAmount, ',', '')," +
            "a.jibun," +
            "b.floor," +
            "CAST(DATE(CONCAT(b.dealYear, '-', LPAD(b.dealMonth, 2, '0'), '-', b.dealDay)) AS LocalDate) ," +
            "(b.area / 3.3057))" +
            "FROM HouseInfo a " +
            "JOIN a.houseDeals b " +
            "WHERE a.dongCode = :dongCode " +
            "ORDER BY CAST(CONCAT(b.dealYear, '-', LPAD(b.dealMonth, 2, '0'), '-', b.dealDay) AS LocalDate) DESC")
    List<HouseDetailDto.ByDong> findHouseListByDongCode(@Param("dongCode") String dongCode);

    @Query("SELECT new com.ssafy.happyhouse.dto.house.HouseDetailDto$ByAptcode(" +
            "a.aptCode, " +
            "a.apartmentName, " +
            "a.buildYear, " +
            "a.lng, " +
            "a.lat, " +
            "REPLACE(hd.dealAmount, ',', ''), " +
            "hd.dealYear, " +
            "hd.dealMonth, " +
            "hd.dealDay, " +
            "hd.floor, " +
            "CONCAT(b.dongName, ' ', a.jibun), " +
            "CONCAT(b.sidoName, ' ', b.gugunName, ' ', b.dongName, ' ', a.roadName), " +
            "CAST(DATE(CONCAT(hd.dealYear, '-', LPAD(hd.dealMonth, 2, '0'), '-', hd.dealDay)) AS LocalDate), " +
            "(hd.area / 3.3057)) " +
            "FROM HouseInfo a JOIN a.houseDeals hd JOIN DongCode b ON a.dongCode = b.dongCode " +
            "WHERE a.aptCode = :aptCode ORDER BY CAST(DATE(CONCAT(hd.dealYear, '-', LPAD(hd.dealMonth, 2, '0'), '-', hd.dealDay)) AS LocalDate) DESC")
    List<HouseDetailDto.ByAptcode> findHouseDetailByAptCode(@Param("aptCode") Long aptCode);


    @Query(value = "SELECT a.apt_code, " +
            "a.apartmen_name, " +
            "CONCAT(b.dong_name, ' ', a.jibun) AS jibunjuso, " +
            "CONCAT(b.sido_name, ' ', b.gugun_name, ' ', b.dongName, ' ', a.roadName) AS dorojuso " +
            "FROM houseinfo a JOIN dongcode b ON a.dong_code = b.dong_code " +
            "WHERE MATCH(a.apartment_name) AGAINST(:apartmentName IN BOOLEAN MODE)",
            nativeQuery = true)
    List<HouseDetailDto.ByAptName> findHouseDetailByAptName(@Param("apartmentName") String apartmentName);

    @Query(value = "WITH RankedData AS (" +
            "  SELECT apt_code, " +
            "         deal_amount, " +
            "         exclusive_area, " +
            "         deal_date, " +
            "         apartment_name, " +
            "         lng, " +
            "         lat, " +
            "         ROW_NUMBER() OVER (PARTITION BY apt_code ORDER BY deal_date DESC) AS rn " +
            "  FROM ( " +
            "         SELECT a.apt_code, " +
            "                REPLACE(a.deal_amount, ',', '') / 10000 AS deal_amount, " +
            "                (a.area / 3.3057) AS exclusive_area, " +
            "                DATE(CONCAT(a.deal_year, '-', LPAD(a.deal_month, 2, '0'), '-', a.deal_day)) AS deal_date, " +
            "                b.apartment_name, " +
            "                b.lng, " +
            "                b.lat " +
            "           FROM housedeal a " +
            "           RIGHT OUTER JOIN ( " +
            "                            SELECT apt_code, apartment_name, lng, lat " +
            "                              FROM houseInfo " +
            "                             WHERE dong_code = ( " +
            "                                                 SELECT dong_code " +
            "                                                   FROM dongcode " +
            "                                                  WHERE gugun_name = :gugunName AND dong_name = :dongName " +
            "                                               ) " +
            "                          ) b " +
            "             ON a.apt_code = b.apt_code " +
            "          ORDER BY deal_date DESC " +
            "       ) a " +
            ") " +
            "SELECT apt_code as aptCode, " +
            "       deal_amount as dealAmount, " +
            "       exclusive_area as exclusiveArea, " +
            "       deal_date as dealDate, " +
            "       apartment_name as apartmentName, " +
            "       lng, " +
            "       lat " +
            "  FROM RankedData " +
            " WHERE rn = 1;",
            nativeQuery = true)
    List<MapMarkerInfoProjection> findApartListByAddressName(@Param("gugunName") String gugunName, @Param("dongName") String dongName);

    @Query(value = "SELECT b.gugun_name AS gugunName, " +
            "a.avgDealAmount, " +
            "b.lat, " +
            "b.lng " +
            "FROM (" +
            "    SELECT (b.dong_code DIV 100000) AS dongcode, " +
            "           ROUND(AVG(CAST(REPLACE(a.deal_amount, ',', '') AS UNSIGNED)) / 10000, 1) AS avgDealAmount " +
            "    FROM housedeal a " +
            "    JOIN houseinfo b ON a.apt_code = b.apt_code " +
            "    WHERE a.deal_year > YEAR(CURDATE()) - 5 " +
            "    GROUP BY b.dong_code DIV 100000" +
            ") a " +
            "JOIN sigungucode b ON (a.dongcode * 100000) = b.dong_code;",
            nativeQuery = true)
    List<MapGugunMarkerInfoProjection> findGugunAvgDealAmount();
}
