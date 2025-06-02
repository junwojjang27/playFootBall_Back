package com.football.playFootball.controller.login;

import com.football.playFootball.domain.login.UserInfo;
import com.football.playFootball.dto.login.LoginRegisterDto;
import com.football.playFootball.dto.login.LoginRequestDto;
import com.football.playFootball.service.login.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.football.playFootball.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/playFootBall/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceImpl loginService;

    @Autowired
    private JwtUtil jwtUtil;

    // http://localhost:8088/playFootBall/login/login?userId=test123
    //그냥 RequestMapping으로 다 써도 되지만, get/post/patch/delete 등으로 구분하는게 더 명확함 (권장됨)
    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginRequestDto dto) {
        /*boolean exists = loginService.existsById(userId);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);
        return ResponseEntity.ok(result);*/

        UserInfo user = loginService.login(dto.getUserId(), dto.getPasswd());

        if (user != null) {
            // ✅ JWT 발급
            String token = jwtUtil.generateToken(user.getUserId(), user.getNickNm());

            // ✅ 응답에 사용자 정보 + 토큰 포함
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("userId", user.getUserId());
            result.put("nickname", user.getNickNm());

            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRegisterDto dto) {
        if (loginService.existsById(dto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 아이디입니다.");
        }

        loginService.saveUser(dto); // insert 처리
        return ResponseEntity.ok("회원가입 완료!");
    }

}
