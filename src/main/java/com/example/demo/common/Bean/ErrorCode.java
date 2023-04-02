package com.example.demo.common.Bean;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글 정보를 찾을 수 없습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원정보를 찾을 수 없습니다."),

    USER_EMAIL_FOUND(HttpStatus.FOUND, "중복 가입된 이메일 입니다."),

    USER_USERNM_FOUND(HttpStatus.FOUND, "중복 가입된 아이디 입니다."),

    USERPW_NOT_FOUND(HttpStatus.NOT_FOUND, "비밀번호 오류 입니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "파라메터 오류입니다."),

    USER_JOIN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입 도중 내부 서버 오류입니다."),

    USER_LOGIN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "로그인 도중 내부 서버 오류입니다."),


    ;

    private final HttpStatus status;
    private final String message;

}