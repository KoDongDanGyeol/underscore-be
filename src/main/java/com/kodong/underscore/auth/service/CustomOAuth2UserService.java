package com.kodong.underscore.auth.service;

import com.kodong.underscore.auth.dto.*;
import com.kodong.underscore.auth.entity.User;
import com.kodong.underscore.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("User : {}", oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        switch (registrationId) {
            case "naver":
                oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
                break;

            case "google":
                oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
                break;

            case "kakao":
                oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
                break;

            default:
                return null;
        }

        // 서버에서 유저 판별 위함
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        User existUser = userRepository.findByUsername(username);
        if (existUser == null) {
            User user = new User(username, oAuth2Response.getName(), oAuth2Response.getEmail(), "ROLE_USER");
            userRepository.save(user);
            UserDTO userDTO = new UserDTO("ROLE_USER", oAuth2Response.getName(), username);
            return new CustomOAuth2User(userDTO);
        }

        // 이미 존재하는 회원이라면 update
        else {
            existUser.updateNameAndEmail(oAuth2Response.getName(), oAuth2Response.getEmail());
            UserDTO userDTO = new UserDTO("ROLE_USER", oAuth2Response.getName(), existUser.getUsername());
            return new CustomOAuth2User(userDTO);
        }

    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser"))
            throw new RuntimeException("로그인 된 상태가 아닙니다.");

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getUsername();
        return userRepository.findByUsername(username);
    }



}
