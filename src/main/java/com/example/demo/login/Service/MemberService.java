package com.example.demo.login.Service;

import com.example.demo.common.Bean.CustomException;
import com.example.demo.common.Bean.ErrorCode;
import com.example.demo.login.Bean.MemberRepository;
import com.example.demo.login.Dto.MemberDto;
import com.example.demo.login.Dto.MemberModel;
import com.example.demo.login.JwtToken.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private BCryptPasswordEncoder pwEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public int userJoin(String UserNm, String UserEamil, String UserPw){

        MemberModel memberModel = MemberModel.builder()
                .password(pwEncoder.encode(UserPw))  //비밀번호 인코딩
                .userNm(UserNm)
                .userEmail(UserEamil)
                .build();

        return memberRepository.save(memberModel).getUid();
    }

    // 회원탈퇴
    @Transactional
    public boolean userJoinOut(MemberDto memberDto){

        memberRepository.deleteByUid(memberDto.getUid());

        return true;
    }

    // 로그인
    @Transactional
    public String login(MemberDto memberDto){
        String jwtToken;

        // 로그인에 성공하면 email, roles 로 토큰 생성 후 반환
        jwtToken = jwtTokenProvider.createToken(memberDto.getUserEmail());

        return jwtToken;
    }


    // 회원 여부 체크, uid 조회
    @Transactional
    public MemberDto userJoinChk(String UserId){
        MemberDto memberDto;

        try{
            memberDto = memberRepository.findByUserNm(UserId);

            // 회원가입 여부 확인
            if(memberDto == null){
                memberDto = memberRepository.findByUserEmail(UserId);

                // 회원가입 여부 확인
                if(memberDto == null){
                    throw new CustomException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
                }
            } else {
                return memberDto;
            }

        } catch (Exception ex){
            // 입력값 오류
            throw new CustomException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        }
        return memberDto;
    }


    // 회원 여부 체크
    @Transactional
    public boolean userJoinYnChk(String UserNm, String UserEamil){
        MemberDto memberDto;

        memberDto = memberRepository.findByUserNm(UserNm);

        // 회원가입 여부 확인
        if(memberDto == null){
            memberDto = memberRepository.findByUserEmail(UserEamil);

            // 회원가입 여부 확인
            if(memberDto == null){
                return true;
            }
        }

        return false;
    }
}