package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service  // 이 클래스가 Spring의 서비스 레이어 역할을 하는 Bean임을 나타냅니다.
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;  // 사용자 정보를 조회하기 위해 UserRepository를 주입받습니다.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 이름으로 사용자를 조회합니다. 결과가 Optional로 반환됩니다.
        Optional<SiteUser> siteUserOpt = this.userRepository.findByUsername(username);

        // 사용자를 찾지 못한 경우 예외를 던집니다.
        if (siteUserOpt.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // 조회된 사용자 정보를 가져옵니다.
        SiteUser siteUser = siteUserOpt.get();
        List<GrantedAuthority> authorities = new ArrayList<>();  // 사용자의 권한 목록을 저장할 리스트입니다.

        // 사용자 이름이 "admin"인 경우 관리자 권한을 부여합니다.
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            // 그 외의 사용자는 기본 사용자 권한을 부여합니다.
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        // UserDetails 객체를 생성하여 반환합니다. 
        // 이 객체는 스프링 시큐리티가 인증 과정을 처리하는 데 사용됩니다.
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
