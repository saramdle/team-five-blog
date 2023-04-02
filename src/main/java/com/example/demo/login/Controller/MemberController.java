package com.example.demo.login.Controller;

import com.example.demo.common.Bean.CustomException;
import com.example.demo.common.Bean.ErrorCode;
import com.example.demo.login.Dto.MemberDto;
import com.example.demo.login.Dto.MemberModel;
import com.example.demo.login.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@CrossOrigin
@RestController
public class MemberController {

    private final MemberService memberService;
    private BCryptPasswordEncoder pwEncoder;

    /**
     * 회원가입 API
     **/
    @PostMapping("/join")
    public boolean join(@RequestBody MemberModel memberModel) {
        try{
            // 입력한 이메일, 아이디 체크
            String resultCode = memberService.userJoinYnChk(memberModel.getUserNm(), memberModel.getUserEmail());
            if(resultCode == "UserEmail"){
                throw new CustomException(ErrorCode.USER_EMAIL_FOUND);
            } else if(resultCode == "UserNm"){
                throw new CustomException(ErrorCode.USER_USERNM_FOUND);
            }

            // 회원가입
            memberService.userJoin(memberModel.getUserNm(), memberModel.getUserEmail(), memberModel.getPassword());
        }catch (Exception ex){
            throw new CustomException(ErrorCode.USER_JOIN_ERROR);
        }
        return true;
    }

    /** 회원탈퇴 API **/
    @PostMapping("/joinOut")
    public boolean joinOut(@RequestBody MemberModel memberModel) {
        try{
            // 회원가입 여부 및 uid 조회
            MemberDto memberDto = memberService.userJoinChk(memberModel.getUserId());

            // 회원 비밀번호, 입력한 비밀번호 체크
            if (!pwEncoder.matches(memberModel.getPassword(), memberDto.getUserPw())) {
                throw new CustomException(ErrorCode.USERPW_NOT_FOUND);
            }

            // 회원탈퇴
            memberService.userJoinOut(memberDto);

        }catch (Exception ex){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return true;
    }

    /** 로그인 API **/
    @PostMapping("/login")
    public String login(@RequestBody MemberModel memberModel) {
        String jwtToken;

        try{
            // 회원가입 여부 및 uid 조회
            MemberDto memberDto = memberService.userJoinChk(memberModel.getUserId());

            // 회원 비밀번호, 입력한 비밀번호 체크
            if (!pwEncoder.matches(memberModel.getPassword(), memberDto.getUserPw())) {
                throw new CustomException(ErrorCode.USERPW_NOT_FOUND);
            }

            // 로그인 토큰 발행
            jwtToken = memberService.login(memberDto);

        }catch (Exception ex){
            throw new CustomException(ErrorCode.USER_LOGIN_ERROR);
        }
        return jwtToken;
    }
}
