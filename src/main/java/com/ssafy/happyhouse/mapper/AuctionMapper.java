package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.auction.Auction;

import java.util.List;


public interface AuctionMapper {

    List<Auction> getAuctionsByGuGunCode(String gugunCode);

    Auction getAuctionById(Long id);
}
