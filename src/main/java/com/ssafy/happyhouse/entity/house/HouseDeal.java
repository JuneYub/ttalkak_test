package com.ssafy.happyhouse.entity.house;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "housedeal")
public class HouseDeal {

    @Id
    private Long no;

    @ManyToOne
    @JoinColumn(name = "aptCode")
    private HouseInfo houseInfo;

    private String dealAmount;

    private String dealYear;

    private String dealMonth;

    private String dealDay;

    private Double area;

    private String floor;

    private String cancelDealType;

}