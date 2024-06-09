package com.ssafy.happyhouse.entity.auction;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "auctions")
public class Auction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String court;
    private String productUsage;
    private String location;
    private String appraisalValue;
    private String minimumSalePrice;
    private String saleDate;
    private String status;
    private String sido;
    private String gugun;
    private String dong;
    private String dongCode;
    private String lng;
    private String lat;

    public void updateLocation(AuctionEditor auctionEditor) {
        this.lat = auctionEditor.getLat();
        this.lng = auctionEditor.getLng();
        this.sido = auctionEditor.getSido();
        this.gugun = auctionEditor.getGugun();
        this.dong = auctionEditor.getDong();
    }

}
