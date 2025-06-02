package com.football.playFootball.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "ThisIsMySuperSecretKeyForJwtToken1234567890"; // 32자 이상
//    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 1; // 1시간
    private static final long EXPIRATION_TIME = 10000; // 10초

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ✅ 토큰 생성
    public String generateToken(String userId, String nickNm) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("nickNm", nickNm)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ 토큰에서 사용자 아이디 꺼내기
    public String extractUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // ✅ 토큰에서 사용자 닉네임 꺼내기
    public String extractNickNm(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("nickNm", String.class);
    }

    // ✅ 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            // 명시적으로 만료 시간 체크
            return !claims.getBody().getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ✅ 만료 시간 추출
    public Date extractExpiration(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getExpiration();
    }

    // ✅ 토큰이 만료되었는지 여부
    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }
}
