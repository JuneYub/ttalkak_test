package com.ssafy.happyhouse.entity.dongcode;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "dongcode")
public class DongCode {

    @Id
    private String dongCode;

    private String sidoName;
    private String gugunName;
    private String dongName;
}
