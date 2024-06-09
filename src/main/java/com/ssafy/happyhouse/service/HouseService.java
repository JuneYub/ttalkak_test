package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.house.HouseDetailDto;
import com.ssafy.happyhouse.entity.house.House;
import com.ssafy.happyhouse.entity.house.HouseDeal;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.EntityNotFoundException;
import com.ssafy.happyhouse.mapper.HouseMapper;
import com.ssafy.happyhouse.repository.HouseDealRepository;
import com.ssafy.happyhouse.repository.HouseRepository;
import com.ssafy.happyhouse.request.AddressName;
import com.ssafy.happyhouse.response.MapGugunMarkerInfo;
import com.ssafy.happyhouse.response.MapGugunMarkerInfoProjection;
import com.ssafy.happyhouse.response.MapMarkerInfo;
import com.ssafy.happyhouse.response.MapMarkerInfoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;
    private final HouseDealRepository houseDealRepository;

    /**
     * 동 번호로 아파트 거래 내역을 조회
     * @param dongCode
     * @return
     */
    public List<HouseDetailDto.ByDong> findHouseDealByDongCode(String dongCode){

        List<HouseDetailDto.ByDong> findHouseList = houseRepository.findHouseListByDongCode(dongCode);

        return findHouseList;
    }

    /**
     * 아파트 코드를 통해 아파트 상세 정보 조회
     * @param aptCode
     * @return
     */
    public List<HouseDetailDto.ByAptcode> findHouseDealByAptCode(Long aptCode){

        List<HouseDetailDto.ByAptcode> findHouseDeal = houseRepository.findHouseDetailByAptCode(aptCode);

        return findHouseDeal;
    }

    /**
     * 아파트명으로 아파트 정보를 검색
     * @param apartmentName
     * @return
     */
    public List<HouseDetailDto.ByAptName> findHouseDealByName(String apartmentName) {

        List<HouseDetailDto.ByAptName> findHouseList = houseRepository.findHouseDetailByAptName(apartmentName);

        return findHouseList;
    }

    /**
     * 주소를 기반으로 모든 아파트 정보를 조회
     * @param addressName
     * @return
     */
    public List<MapMarkerInfo> findApartListByAddressName(AddressName addressName) {
        List<MapMarkerInfoProjection> projections = houseRepository.findApartListByAddressName(addressName.getGugunName(), addressName.getDongName());

        return projections.stream()
                .map(projection -> MapMarkerInfo.builder()
                        .aptCode(projection.getAptCode())
                        .dealAmount(projection.getDealAmount())
                        .exclusiveArea(projection.getExclusiveArea())
                        .dealDate(projection.getDealDate())
                        .apartmentName(projection.getApartmentName())
                        .lng(projection.getLng())
                        .lat(projection.getLat())
                        .build())
                .collect(Collectors.toList());

    }

    /**
     * 구군 5년치 평균 실거래가 정보
     * @return
     */
    public List<MapGugunMarkerInfo> findGugunAvgDealAmount() {

        List<MapGugunMarkerInfoProjection> projections = houseRepository.findGugunAvgDealAmount();

        return projections.stream()
                .map(projection -> new MapGugunMarkerInfo().builder()
                        .gugunName(projection.getGugunName())
                        .avgDealAmount(projection.getAvgDealAmount())
                        .lng(projection.getLng())
                        .lat(projection.getLat())
                        .build())
                .collect(Collectors.toList());
    }
}
