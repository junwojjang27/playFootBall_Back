package com.football.playFootball.domain.login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    boolean existsByUserId(String userId);
    boolean existsByNickNm(String nickNm);
    UserInfo findByUserId(String userId);
}