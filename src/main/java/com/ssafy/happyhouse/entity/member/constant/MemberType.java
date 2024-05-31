package com.ssafy.happyhouse.entity.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum MemberType {

    LOCAL("LOCAL"), KAKAO("KAKAO");

    private final String memberType;

    public static MemberType from(String type){

        return MemberType.valueOf(type.toUpperCase());
    }

    public static boolean isMemberType(String type){
        try {
            MemberType.valueOf(type.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
