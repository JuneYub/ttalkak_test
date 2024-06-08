package com.ssafy.happyhouse.response;

import lombok.*;
import org.checkerframework.checker.units.qual.N;

@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class MapMarkerInfo {

    private String aptCode;
    private String dealAmount;
    private String exclusiveArea;
    private String dealDate;
    private String apartmentName;
    private Double lng;
    private Double lat;

}
