package com.ssafy.happyhouse.dto.house;

import lombok.*;

import java.time.LocalDate;


public class HouseDetailDto {

    @Data
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ByDong {
        private Long aptCode;
        private String apartmentName;
        private int buildYear;
        private String dealAmount;
        private String jibun;
        private String floor;
        private LocalDate dealDate;
        private double exclusiveArea;
    }

    @Data
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ByAptcode {
        private Long aptCode;
        private String apartmentName;
        private int buildYear;
        private String lng;
        private String lat;
        private String dealAmount;
        private String dealYear;
        private String dealMonth;
        private String dealDay;
        private String floor;
        private String jibunjuso;
        private String dorojuso;
        private LocalDate dealDate;
        private double exclusiveArea;
    }

    @Data
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class ByAptName {
        private Long aptCode;
        private String apartmentName;
        private String jibunjuso;
        private String dorojuso;
    }

}
