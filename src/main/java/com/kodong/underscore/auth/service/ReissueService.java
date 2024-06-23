package com.kodong.underscore.auth.service;

import com.kodong.underscore.auth.entity.RefreshToken;
import com.kodong.underscore.auth.jwt.JWTUtil;
import com.kodong.underscore.auth.repository.RefreshTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public ResponseEntity<?> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        // get refreshToken
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh"))
                refreshToken = cookie.getValue();
        }

        if (refreshToken == null)
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);

        // expired check
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // refresh token check
        String category = jwtUtil.getCategory(refreshToken);
        if (!category.equals("refresh"))
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);

        // db 에 저장되어 있는 refreshToken 인지 확인
        Boolean isExist = refreshTokenRepository.existsByToken(refreshToken);
        if (!isExist)
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);


        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // make new AccessToken
        String newAccessToken = jwtUtil.createJwt("access", username, role, 600000L);

        // refresh rotate, make new RefreshToken
        // 저장되어 있는 올바른 토큰이라면, 기존 토큰을 db 에서 삭제 후 새로운 토큰 생성
        String newRefreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L);
        refreshTokenRepository.deleteByToken(refreshToken);
        addRefreshToken(username, refreshToken, 86400000L);

        // response
        response.addHeader("Set-Cookie",createResponseCookie("access", newAccessToken));
        response.addHeader("Set-Cookie",createResponseCookie("refresh", newRefreshToken));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);

        return cookie;
    }

    private String createResponseCookie(String key, String value) {
        ResponseCookie cookie = ResponseCookie.from(key, value)
                .domain(".underscore.or.kr")
                .path("/")
                .maxAge(60*60*60)
                .sameSite("None")
                .secure(true) // Secure 속성 설정
                .build();

        return cookie.toString();
    }

    private void addRefreshToken(String username, String token, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        RefreshToken refreshToken = new RefreshToken(username, token, date.toString());

        refreshTokenRepository.save(refreshToken);
    }

}

