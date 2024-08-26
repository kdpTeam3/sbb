package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration  // 이 클래스는 Spring 설정 파일임을 나타냅니다.
@EnableWebSecurity  // Spring Security를 활성화합니다.
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean  // 이 메서드는 스프링 컨테이너에서 관리되는 Bean을 생성합니다.
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        // 보안 설정을 구성하는 메서드입니다.

        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll() )
                // 모든 경로(/**)에 대해 인증 없이 접근을 허용합니다.
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher
("/h2-console/**")))
                        // "/h2-console/**" 경로에 대한 CSRF 보호를 비활성화합니다.
                        // 이는 H2 데이터베이스 콘솔에 접근할 때 CSRF 검증을 피하기 위해 필요합니다.
                .headers((headers)->headers
                		.addHeaderWriter(new XFrameOptionsHeaderWriter(
                				XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                // X-Frame-Options 헤더를 설정하여 페이지가 동일 출처에서만 iframe으로 포함되도록 합니다.
                // 이 설정은 클릭재킹(Clickjacking) 공격을 방지하는 데 사용됩니다.
                // SAMEORIGIN 모드는 같은 도메인 내에서만 페이지를 iframe에 포함할 수 있게 합니다.
                
                .formLogin((formLogin) -> formLogin
             	.loginPage("/user/login")  // 사용자가 로그인할 때 볼 수 있는 페이지를 "/user/login"으로 지정합니다.
                	.defaultSuccessUrl("/"))   // 로그인에 성공하면 사용자가 자동으로 "/" 페이지로 이동하도록 설정합니다.
                	
                .logout((logout) -> logout
                	    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) 
                	    // "/user/logout" URL로 로그아웃 요청이 들어올 때 로그아웃을 처리하도록 설정합니다.
                	    
                	    .logoutSuccessUrl("/")  
                	    // 로그아웃이 성공한 후 사용자를 "/" (홈 페이지)로 리디렉션합니다.
                	    
                	    .invalidateHttpSession(true))  
                	    // 로그아웃 시 현재 세션을 무효화하여 사용자의 모든 세션 데이터를 삭제합니다.
                    ;      

        return http.build(); // 설정을 완료하고 보안 필터 체인을 반환합니다.
    }
    // SecurityConfig 클래스 내부에 추가될 메서드들

    // PasswordEncoder 빈(Bean)을 정의합니다. 
    // 이 빈은 비밀번호를 암호화하는 데 사용됩니다. 
    // BCryptPasswordEncoder는 강력한 해시 함수를 사용해 비밀번호를 안전하게 암호화합니다.
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈(Bean)을 정의합니다. 
    // 이 빈은 인증(authentication) 처리를 담당하는 기본 매니저를 제공합니다.
    // AuthenticationManager는 사용자 로그인 시 인증 작업을 수행합니다.
    // AuthenticationConfiguration은 스프링 시큐리티의 기본 인증 설정을 제공합니다.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    
}
