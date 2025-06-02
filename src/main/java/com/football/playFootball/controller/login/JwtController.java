package com.football.playFootball.controller.login;

import com.football.playFootball.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// JwtController.java
@RestController
@RequestMapping("/playFootBall/jwt")
public class JwtController {

    private final JwtUtil jwtUtil;

    public JwtController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        boolean valid = jwtUtil.validateToken(token);
        if (valid) {
            return ResponseEntity.ok().body("valid");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid");
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid");
        }
        String userId = jwtUtil.extractUserId(token);
        String nickNm = jwtUtil.extractNickNm(token);
        String newToken = jwtUtil.generateToken(userId, nickNm);
        return ResponseEntity.ok(newToken);
    }
}
