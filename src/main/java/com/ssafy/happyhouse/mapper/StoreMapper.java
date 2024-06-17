package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.redis.entity.Store;
import com.ssafy.happyhouse.request.AddressName;

import java.util.List;

public interface StoreMapper {

    List<Store> findALl();

    Store findDongCodeByDongName(AddressName addressName);
}
