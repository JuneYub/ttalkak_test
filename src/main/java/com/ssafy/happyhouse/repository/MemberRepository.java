package com.ssafy.happyhouse.repository;

import com.ssafy.happyhouse.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.username = :username")
    Optional<Member> findByUsername(@Param("username") String username);

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);

    @Query("SELECT m FROM Member m WHERE m.username = :username AND m.password = :password")
    Optional<Member> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT m FROM Member m WHERE m.role = 'EXPERT'")
    List<Member> findExpertList();

    @Query("SELECT m FROM Member m WHERE m.refreshToken = :refreshToekn")
    Optional<Member> findMemberByRefreshToken(String refreshToekn);

    @Modifying
    @Query("UPDATE Member m SET m.refreshToken = :refreshToken, " +
            "m.refreshTokenExpirationTime = :refreshTokenExpireTime WHERE m.id = :id")
    void updateToken(@Param("id") Long id, @Param("refreshToken") String refreshToken, @Param("refreshTokenExpireTime")LocalDateTime refreshTokenExpireTime);

    @Modifying
    @Query("UPDATE Member m SET m.refreshTokenExpirationTime = :now WHERE m.id = :id")
    void expireToken(@Param("id") Long id, @Param("now") LocalDateTime now);

    @Query("SELECT m.email FROM Member m WHERE m.isSubscribed = 1")
    List<String> findRecipients();

    @Query("SELECT m.isSubscribed FROM Member m WHERE m.id = :memberId")
    int checkIsSubscribed(@Param("memberId") Long memberId);

}
