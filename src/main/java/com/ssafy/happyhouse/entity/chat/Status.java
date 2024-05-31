package com.ssafy.happyhouse.entity.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Status {

    LIVE("LIVE"), EXPIRED("EXPIRED");

    private final String status;

    public static Status from(String status) {

        return Status.valueOf(status.toUpperCase());
    }

    public static boolean isStatus(String status) {

        try {
            Status.valueOf(status.toUpperCase());
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
