package com.ssafy.happyhouse.dto.auction;

public interface AuctionProjection {
    Long getId();
    String getCourt();
    String getProductUsage();
    String getLocation();
    String getAppraisalValue();
    String getMinimumSalePrice();
    String getSaleDate();
    String getStatus();
    String getLng();
    String getLat();
    String getDongCode();
}
