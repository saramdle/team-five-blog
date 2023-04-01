package com.example.demo.login.Service;

import com.example.demo.common.Bean.CustomException;
import com.example.demo.common.Bean.ErrorCode;
import com.example.demo.login.Bean.MemberRepository;
import com.example.demo.login.Dto.MemberDto;
import com.example.demo.login.Dto.MemberModel;
import com.example.demo.login.JwtToken.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public int userJoin(MemberDto memberDto){
        MemberModel memberModel = MemberModel.builder()
                .uid(memberDto.getUid())
                .password(passwordEncoder.encode(memberDto.getUserPw()))  //비밀번호 인코딩
                .userName(memberDto.getUserName())
                .userNickNm(memberDto.getUserNickNm())
                .userSignDate(memberDto.getUserSignDate())
                .userEmail(memberDto.getUserEmail())
                .roles(Collections.singletonList("ROLE_USER"))         //roles는 최초 USER로 설정
                .build();

        return memberRepository.save(memberModel).getUid();
    }

    // 회원가입
    @Transactional
    public boolean userJoinOut(MemberDto memberDto){
        memberRepository.deleteByUid(memberDto.getUid());

        return true;
    }

    // 로그인
    @Transactional
    public String login(MemberDto memberDto){
        MemberModel memberModel = memberRepository.findByUid(memberDto.getUid())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(memberDto.getUserPw(), memberModel.getPassword())) {
            throw new CustomException(ErrorCode.USERPW_NOT_FOUND);
        }
        // 로그인에 성공하면 email, roles 로 토큰 생성 후 반환
        return jwtTokenProvider.createToken(memberModel.getUserEmail(), memberModel.getRoles());
    }
}