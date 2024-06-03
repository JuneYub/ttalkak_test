package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.entity.auction.Auction;
import com.ssafy.happyhouse.mapper.AuctionMapper;
import com.ssafy.happyhouse.mapper.StoreMapper;
import com.ssafy.happyhouse.repository.AuctionRepository;
import com.ssafy.happyhouse.repository.DongCodeRepository;
import com.ssafy.happyhouse.repository.StoreRepository;
import com.ssafy.happyhouse.request.AddressName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final DongCodeRepository dongCodeRepository;
    private final AuctionRepository auctionRepository;

    public List<Auction> getAuctionsByGuGunCode(AddressName addressName) {

        String dongCode = dongCodeRepository.findDongCodeByDongName(addressName.getGugunName(), addressName.getDongName());
        String gugunCode = dongCode.substring(0,5) + "00000";

        List<Auction> tmp = auctionRepository.getAuctionsByGuGunCode(gugunCode);
        return tmp;
    }

    public Auction getAuctionById(Long id) {
        return auctionRepository.getAuctionById(id);
    }
}
