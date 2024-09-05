package com.mysite.sbb.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; 
    private String username;
    private String email;
    private String password;

    // 소셜 로그인 제공자 정보 (네이버, 카카오 등)
    @Column(nullable = true)  // 이 필드는 소셜 로그인 사용자에게만 사용되므로 nullable로 설정
    private String provider;

    // getter와 setter는 Lombok이 자동으로 생성 (따로 작성할 필요 없음)

    // 추가로 필요하다면 수동으로 getter/setter를 작성할 수 있습니다.
}
