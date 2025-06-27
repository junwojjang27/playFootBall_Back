package com.football.playFootball.service.login;

import com.football.playFootball.domain.login.UserInfo;
import com.football.playFootball.domain.login.UserInfoRepository;
import com.football.playFootball.dto.login.LoginRegisterDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional  // 클래스 전체에 트랜잭션 적용
@RequiredArgsConstructor
public class LoginServiceImpl {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean existsById(String userId) {
        System.out.println("==== TEST QUERY START ====");
        return userInfoRepository.existsByUserId(userId);
    }

    public boolean existsByNickNm(String nickNm) {
        System.out.println("==== TEST QUERY START ====");
        return userInfoRepository.existsByNickNm(nickNm);
    }

    public UserInfo login(String userId, String rawPassword) {
        // 1. 사용자 조회
        UserInfo user = userInfoRepository.findByUserId(userId);
        if (user == null) {
            return null; // 사용자 없음
        }

        // 2. 비밀번호 일치 여부 확인
        boolean isMatch = passwordEncoder.matches(rawPassword, user.getPasswd());
        if(isMatch) {
            return user;
        } else {
            return null;
        }
    }

    public void saveUser(LoginRegisterDto dto) {
        UserInfo user = new UserInfo();
        user.setUserId(dto.getUserId());

        // ✅ 평문 비밀번호 → 암호화 후 저장
        String encryptedPassword = passwordEncoder.encode(dto.getPasswd());
        user.setPasswd(encryptedPassword);

        user.setUserNm(dto.getUserNm());
        user.setNickNm(dto.getNickNm());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setBirth(dto.getBirth());
        user.setRole("99");
        //user.setCreateDt(LocalDateTime.now()); //insertable=fasle 설정으로 자동 설정가능..

        //save함수는 PK가 중복되지 않을 떈 INSERT하고 중복되면 자동으로 UPDATE처리함..
        userInfoRepository.save(user);
    }
}

