package com.football.playFootball.controller.login;

import com.football.playFootball.dto.login.LoginRegisterDto;
import com.football.playFootball.service.login.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/playFootBall/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceImpl loginService;

    // http://localhost:8088/playFootBall/login/login?userId=test123
    //그냥 RequestMapping으로 다 써도 되지만, get/post/patch/delete 등으로 구분하는게 더 명확함 (권장됨)
    @GetMapping("/login")
    public ResponseEntity<Map<String, Boolean>> doLogin(@RequestParam String userId) {
        boolean exists = loginService.existsById(userId);

        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);

        return ResponseEntity.ok(result);
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
