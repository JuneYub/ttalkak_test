package com.ssafy.happyhouse.entity.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberEditor {
    private String username;
    private String password;
    private String email;
    private int isSubscribed;

    @Builder
    public MemberEditor(String username, String password, String email, int isSubscribed) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isSubscribed  = isSubscribed;
    }
}
