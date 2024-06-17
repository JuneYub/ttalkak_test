package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.member.Member;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberMapper {

    List<Member> findAll();

    Member findById(Long id);

    Member findByUsernameAndPassword(Map<String, String> map);

    List<Member> findExpertList();

    void join(Member member);

    void oauthJoin(Member member);

    void update(Map<String, Object> map);

    void delete(Long id);

    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);

    Member findMemberByRefreshToken(String refreshToken);

    void updateToken(Map<String, Object> map);

    void expireToken(Map<String, Object> map);
}

