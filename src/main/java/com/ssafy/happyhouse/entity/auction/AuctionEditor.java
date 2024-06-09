package com.ssafy.happyhouse.entity.auction;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuctionEditor {
    private String lat;
    private String lng;
    private String sido;
    private String gugun;
    private String dong;

    @Builder
    AuctionEditor(String lat, String lng, String sido, String gugun, String dong) {
        this.lat = lat;
        this.lng = lng;
        this.sido = sido;
        this.gugun = gugun;
        this.dong = dong;
    }
}
