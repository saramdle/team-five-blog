package com.example.demo.login.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    @NotNull
    @Size(min = 3, max = 100)
    private int uid;

    @NotNull
    @Size(min = 8, max = 300)
    private String userPw;

    @NotNull
    @Size(min = 8, max = 300)
    private String userNm;

    @Size(min = 8, max = 14)
    private String userSignDate;

    @Size(min = 3, max = 100)
    private String userEmail;

}
