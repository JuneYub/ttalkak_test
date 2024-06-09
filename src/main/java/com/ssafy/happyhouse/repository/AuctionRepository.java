package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.entity.auction.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query(value = "SELECT id, court, product_usage AS productUsage, location, appraisal_value AS appraisalValue, " +
            "minimum_sale_price AS minimumSalePrice, sale_date AS saleDate, status, lng, lat, dong_code AS dongCode " +
            "FROM auctions WHERE CONCAT(SUBSTRING(dong_code, 1, 5), '00000') = :gugunCode", nativeQuery = true)
    List<Auction> getAuctionsByGuGunCode(@Param("gugunCode") String gugunCode);

    @Query("SELECT a FROM Auction a WHERE a.lng IS NULL")
    List<Auction> findAuctionsByLngIsNull();

}
