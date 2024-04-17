package com.kodong.underscore.auth.oauth2;

import com.kodong.underscore.auth.dto.CustomOAuth2User;
import com.kodong.underscore.auth.entity.RefreshToken;
import com.kodong.underscore.auth.jwt.JWTUtil;
import com.kodong.underscore.auth.service.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        log.info("success, username: {}", username);

        // create tokens
        String accessToken = jwtUtil.createJwt("access", username, role, 600000L);
        String refreshToken = jwtUtil.createJwt("refresh", username, role, 86400000L);

        log.info("accessToken: {}", accessToken);
        log.info("refreshToken: {}", refreshToken);

        // refresh token 저장
        saveRefreshToken(username, refreshToken, 86400000L);

        log.info("token success..")

        response.setHeader("access", accessToken);
        response.addCookie(createCookie("refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect("https://underscore.or.kr/auth/join/complete");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void saveRefreshToken(String username, String token, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshToken = new RefreshToken(username, token, date.toString());
        refreshTokenService.save(refreshToken);
    }
}

