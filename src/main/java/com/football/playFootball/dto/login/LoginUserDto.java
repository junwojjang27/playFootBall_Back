package com.football.playFootball.dto.login;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginUserDto {
    private String userId;
    private String passwd;
    private String userNm;
    private String nickNm;
    private String email;
    private String phone;
    private String address;
    private String birth;
    private String grade;
    private String role;

    public LoginUserDto(String userId, String nickNm) {
        this.userId = userId;
        this.nickNm = nickNm;
    }
}
