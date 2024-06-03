package com.ssafy.happyhouse.entity.store;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "store")
public class Store {
    @Id
    private String storeCode;

    private String storeName;

    private String mainCategoryCode;

    private String mainCategoryName;

    private String subCategoryCode;

    private String subCategoryName;

    private String dongCode;

    private String dongName;

    private String doro;

    private String lng;

    private String lat;
}
