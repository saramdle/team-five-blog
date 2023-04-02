package com.example.demo.login.Controller;

import com.example.demo.common.Bean.CustomException;
import com.example.demo.common.Bean.ErrorCode;
import com.example.demo.login.Dto.MemberModel;
import com.example.demo.login.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private BCryptPasswordEncoder pwEncoder;

    /**
     * 회원가입 API
     **/
    @PostMapping("/join")
    public int join(@RequestBody String UserNm, String UserEamil, String UserPw) {
        int uid = -1;
        try{
            // 입력한 이메일, 아이디 체크
            if(memberService.userJoinYnChk(UserNm, UserEamil) == false){
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            }

            // 회원가입
            uid = memberService.userJoin(UserNm, UserEamil, UserPw);
        }catch (Exception ex){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return uid;
    }

    /** 회원탈퇴 API **/
    @PostMapping("/joinOut")
    public boolean joinOut(@RequestBody String UserId, String UserPw) {
        try{
            // 회원가입 여부 및 uid 조회
            MemberModel memberModel = memberService.userJoinChk(UserId);

            // 회원 비밀번호, 입력한 비밀번호 체크
            if (!pwEncoder.matches(UserPw, memberModel.getPassword())) {
                throw new CustomException(ErrorCode.USERPW_NOT_FOUND);
            }

            // 회원탈퇴
            memberService.userJoinOut(memberModel);

        }catch (Exception ex){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return true;
    }

    /** 로그인 API **/
    @PostMapping("/login")
    public String login(@RequestBody String UserId, String UserPw) {
        String jwtToken;

        try{
            // 회원가입 여부 및 uid 조회
            MemberModel memberModel = memberService.userJoinChk(UserId);

            // 회원 비밀번호, 입력한 비밀번호 체크
            if (!pwEncoder.matches(UserPw, memberModel.getPassword())) {
                throw new CustomException(ErrorCode.USERPW_NOT_FOUND);
            }

            // 로그인 토큰 발행
            jwtToken = memberService.login(memberModel);

        }catch (Exception ex){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return jwtToken;
    }
}
