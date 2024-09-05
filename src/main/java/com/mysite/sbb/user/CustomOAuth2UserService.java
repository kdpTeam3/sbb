package com.mysite.sbb.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UserService userService;

    @Autowired
    public CustomOAuth2UserService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 네이버와 카카오를 구분하여 처리
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String username;
        String email;

        if ("naver".equals(registrationId)) {
            // 네이버 사용자 정보
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            username = (String) response.get("nickname");
            email = (String) response.get("email");
        } else if ("kakao".equals(registrationId)) {
            // 카카오 사용자 정보
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            username = (String) profile.get("nickname");
            email = (String) kakaoAccount.get("email");
        } else {
            throw new IllegalArgumentException("Unsupported registrationId: " + registrationId);
        }

        // 사용자 정보를 DB에 저장하거나 가져옵니다.
        SiteUser user = userService.getUser(username);
        if (user == null) {
            user = new SiteUser();
            user.setUsername(username);
            user.setEmail(email);
            userService.create(user.getUsername(), user.getEmail(), "");
        }

        // 사용자 정보를 반환합니다.
        Map<String, Object> customAttributes = Map.of(
            "id", username,
            "name", username
        );

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            customAttributes,
            "name" // 사용자 이름 또는 닉네임을 기본 키로 설정
        );
    }
}
