package com.mysite.sbb.user;


import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // final로 선언된 필드를 자동으로 초기화하는 생성자를 생성합니다.
 // 이 클래스가 서비스 역할을 하는 Spring Bean임을 나타냅니다.
@Service public class UserService {
    
    private final UserRepository userRepository;  
    // UserRepository를 의존성 주입을 통해 사용합니다.
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        // 새로운 사용자(SiteUser)를 생성하는 메서드입니다.
        
        SiteUser user = new SiteUser();  
        // 새로운 SiteUser 객체를 생성합니다.
        
        user.setUsername(username);  
        // 사용자 이름을 설정합니다.
        
        user.setEmail(email);  
        // 사용자 이메일을 설정합니다.
        
        user.setPassword(passwordEncoder.encode(password));
        
        this.userRepository.save(user);  
        // 암호화된 비밀번호와 함께 새로운 사용자를 데이터베이스에 저장합니다. 
        
        return user;  
        // 생성된 사용자 객체를 반환합니다.
    }
    
    //UserServic에 siteUser를 조회 할 수 있는 getUser 추가
    // => 사용자를 확인해서 사용자의 정보를 가져오겠다 (엔티티 정보가 필요)
    public SiteUser getUser(String username) {
    	Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
    	// findByUsername을 통해서 사이트 유저를 가져오고 findByUsername는 Optional로 넘겨줌
    	if (siteUser.isPresent()) {
    		return siteUser.get();
		}else {
			throw new DataNotFoundException("siteuser not found");
		} 
    	// 있는지 확인하고 넘겨줌 없으면 DataNotFoundException이라는 예외를 발생시킴
    }
}












