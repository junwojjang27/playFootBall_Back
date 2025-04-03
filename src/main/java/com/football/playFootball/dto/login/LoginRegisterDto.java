package com.football.playFootball.dto.login;

import lombok.*;

@Data
public class LoginRegisterDto {
    private String userId;
    private String passwd;
    private String userNm;
    private String nickNm;
    private String email;
    private String phone;
    private String address;
    private String birth;
}
