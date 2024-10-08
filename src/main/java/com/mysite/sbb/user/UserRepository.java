package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUsername(String username); // userId 대신 username 사용
    Optional<SiteUser> findByEmail(String email); // 이메일로 사용자 찾기 추가
}
