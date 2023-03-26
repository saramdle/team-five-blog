package com.example.demo.login.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModel {

    private int uid;

    private String userPw;

    private String userNm;

    private String userNickNm;

    private String userSignDate;

    private String userEmail;

    private String userImg;
}
