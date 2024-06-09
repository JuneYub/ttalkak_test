package com.ssafy.happyhouse.entity.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberEditor {
    private String username;
    private String password;
    private String email;

    @Builder
    public MemberEditor(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
