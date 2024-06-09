package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.member.MemberDto;
import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.entity.member.MemberEditor;
import com.ssafy.happyhouse.entity.member.constant.MemberType;
import com.ssafy.happyhouse.entity.member.constant.Role;
import com.ssafy.happyhouse.global.error.ErrorCode;
import com.ssafy.happyhouse.global.error.exception.BusinessException;
import com.ssafy.happyhouse.global.error.exception.EntityNotFoundException;
import com.ssafy.happyhouse.global.token.JwtTokenDto;
import com.ssafy.happyhouse.mapper.MemberMapper;
import com.ssafy.happyhouse.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void validUsername(String username){

        Optional<Member> findMember = memberRepository.findByUsername(username);

        if (findMember.isPresent())
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_USERNAME);
    }


    public List<Member> findAll() {

        List<Member> findAll = memberRepository.findAll();

        if (findAll.isEmpty())
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS);

        return findAll;
    }

    public Member findByUsernameAndPassword(String username, String password){

        Member findMember = memberRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        return findMember;
    }

    public Member findById(Long id){

        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        return findMember;
    }

    public Member findByEmail(String email){

        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

    }

    public List<Member> findExpertList() {

        return memberRepository.findExpertList();
    }

    @Transactional
    public void join(MemberDto.Join dto){

        validUsername(dto.getUsername());
        //validEmail(dto.getEmail());

        Member joinMember = Member.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .role(Role.USER)
                .memberType(MemberType.LOCAL)
                .nickname(dto.getNickname())
                .profile("https://api.dicebear.com/8.x/pixel-art/svg?seed="+dto.getUsername())
                .build();

        memberRepository.save(joinMember);
    }

    @Transactional
    public void joinByEntity(Member member){

        log.info("Join Member Id : {}", member.getId());
        log.info("Join Member Username : {}", member.getUsername());
        log.info("Join Member Email : {}", member.getEmail());
        log.info("Join Member Password : {}", member.getPassword());
        log.info("Join Member Role : {}", member.getRole());
        log.info("Join Member Type : {}", member.getMemberType());
        log.info("Join Member NickName : {}", member.getNickname());

        memberRepository.save(member);
    }

    @Transactional
    public void updateMember(MemberDto.Update dto){

        Member member = memberRepository.findByUsername(dto.getUsername())
                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        MemberEditor.MemberEditorBuilder memberEditorBuilder = MemberEditor.builder();

        MemberEditor memberEditor = memberEditorBuilder
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .build();

        member.updateMember(memberEditor);
    }

    @Transactional
    public void deleteMember(Long id){

        memberRepository.deleteById(id);
    }
    public Member findMemberByRefreshToken(String refreshToken){

        Member findMember = memberRepository.findMemberByRefreshToken(refreshToken)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));

        return findMember;
    }

    @Transactional
    public void updateToken(Long id, JwtTokenDto token){

        String refreshToken = token.getRefreshToken();
        LocalDateTime refreshTokenExpireTime = token.getRefreshTokenExpireTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        memberRepository.updateToken(id, refreshToken, refreshTokenExpireTime);
    }

    @Transactional
    public void expireToken(Long id, LocalDateTime now) {

        memberRepository.expireToken(id, now);
    }
}
