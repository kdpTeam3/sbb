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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

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

        // 이메일이 유효하지 않을 경우 예외 처리
        if (email == null) {
            throw new IllegalArgumentException("이메일 정보를 가져올 수 없습니다.");
        }

        // 이메일 중복 체크 및 처리
        Optional<SiteUser> existingUser = userService.findByEmail(email);
        SiteUser user;
        if (existingUser.isPresent()) {
            // 이미 존재하는 사용자는 가져오기
            user = existingUser.get();
        } else {
            // 새로운 사용자 생성
            user = userService.create(username, email, "");
        }

        // 사용자 정보를 반환
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