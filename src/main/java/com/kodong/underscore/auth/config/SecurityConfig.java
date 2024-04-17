package com.kodong.underscore.auth.config;

import com.kodong.underscore.auth.jwt.CustomLogoutFilter;
import com.kodong.underscore.auth.jwt.JWTFilter;
import com.kodong.underscore.auth.jwt.JWTUtil;
import com.kodong.underscore.auth.oauth2.CustomSuccessHandler;
import com.kodong.underscore.auth.service.CustomOAuth2UserService;
import com.kodong.underscore.auth.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final RefreshTokenService refreshTokenService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //HTTP Basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        // JWT 필터 추가
        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 로그아웃 필터
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshTokenService), LogoutFilter.class);


        //oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("http://localhost:3000/auth/join")
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)))
                        .successHandler(customSuccessHandler)
                );


        //경로별 인가 작업
        // todo 페이지별 인가 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**", "/reissue").permitAll());


        //세션 설정 : STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // cors
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://underscore.or.kr"));
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setMaxAge(3600L);
                    configuration.setExposedHeaders(Arrays.asList("Set-Cookie", "Authorization"));
                    return configuration;
                }));

        return http.build();
    }



}
