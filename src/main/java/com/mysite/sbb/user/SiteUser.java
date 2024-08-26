package com.mysite.sbb.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter  // Lombok 라이브러리를 사용해 자동으로 getter 메서드를 생성합니다.
@Setter  // Lombok 라이브러리를 사용해 자동으로 setter 메서드를 생성합니다.
@Entity  // 이 클래스가 JPA 엔티티임을 나타내며, 데이터베이스 테이블과 매핑됩니다.
public class SiteUser {

    @Id  // 이 필드가 엔티티의 기본 키(primary key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    // 기본 키 값을 자동으로 생성하고, 데이터베이스에 위임합니다 (자동 증가).
    private Long id;  // 사용자 고유 ID입니다.

    @Column(unique = true)  
    // 이 필드는 데이터베이스에서 유일해야 하며 중복될 수 없습니다.
    private String username;  // 사용자 이름 (로그인 ID)입니다.

    private String password;  // 암호화된 사용자 비밀번호입니다.

    @Column(unique = true)  
    // 이메일 주소는 데이터베이스에서 유일해야 하며 중복될 수 없습니다.
    private String email;  // 사용자 이메일 주소입니다.
}
