package com.example.demo.login.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberDto {

    @NotNull
    @Size(min = 3, max = 100)
    private int uid;

    @NotNull
    @Size(min = 8, max = 300)
    private String userPw;

    @NotNull
    @Size(min = 8, max = 300)
    private String userName;

    @NotNull
    @Size(min = 8, max = 300)
    private String userNickNm;

    @NotNull
    @Size(min = 8, max = 14)
    private String userSignDate;

    @NotNull
    @Size(min = 3, max = 100)
    private String userEmail;

}
