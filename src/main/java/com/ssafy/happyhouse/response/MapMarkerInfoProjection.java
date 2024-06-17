package com.ssafy.happyhouse.response;

import java.sql.Date;

public interface MapMarkerInfoProjection {
    Long getAptCode();
    String getDealAmount();
    Double getExclusiveArea();
    Date getDealDate();
    String getApartmentName();
    Double getLng();
    Double getLat();
}
