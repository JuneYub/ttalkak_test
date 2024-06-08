package com.ssafy.happyhouse.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class MapGugunMarkerInfo {
    private String gugunName;
    private String avgDealAmount;
    private Double lng;
    private Double lat;
}
