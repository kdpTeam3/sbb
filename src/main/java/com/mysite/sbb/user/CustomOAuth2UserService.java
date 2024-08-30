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

import lombok.RequiredArgsConstructor;
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

        // 네이버에서 반환하는 사용자 정보
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        // 사용자 ID와 닉네임을 가져옵니다.
        String username = (String) response.get("nickname");

        // 사용자 정보를 DB에 저장하거나 가져옵니다.
        SiteUser user = userService.getUser(username);
        if (user == null) {
            user = new SiteUser();
            user.setUsername(username);
            user.setEmail((String) response.get("email"));
            userService.create(user.getUsername(), user.getEmail(), "");
        }

        // 이 값을 사용해 사용자 정보를 구성합니다.
        Map<String, Object> customAttributes = Map.of(
            "id", username, 
            "name", username // 또는 "name", response.get("name")로 실제 이름을 사용할 수 있습니다.
        );

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            customAttributes,
            "name" // 사용자 이름 또는 닉네임을 기본 키로 설정
        );
    }
}
