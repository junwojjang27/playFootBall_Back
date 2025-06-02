package com.football.playFootball.dto.login;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String userId;
    private String passwd;

    // getter, setter
}