package com.football.playFootball.domain.login;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "COM_USERINFO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    @Id
    @Column(name = "USER_ID", length = 20, nullable = false)
    private String userId;

    @Column(name = "PASSWD", length = 255, nullable = false)
    private String passwd;

    @Column(name = "USER_NM", length = 100, nullable = false)
    private String userNm;

    @Column(name = "NICK_NM", length = 30, nullable = false)
    private String nickNm;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "PHONE", length = 11)
    private String phone;

    @Column(name = "ADDRESS", length = 300)
    private String address;

    @Column(name = "BIRTH", length = 8)
    private String birth;

    @Column(name = "GRADE", length = 3)
    private String grade;

    @Column(name = "ROLE", length = 2)
    private String role;

    @Column(name = "CREATE_DT", insertable = false)
    private LocalDateTime createDt;

    @Column(name = "UPDATE_DT", updatable = false)
    private LocalDateTime updateDt;
}