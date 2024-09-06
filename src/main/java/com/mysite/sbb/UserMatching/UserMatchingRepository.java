package com.mysite.sbb.UserMatching;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMatchingRepository extends JpaRepository<UserMatching, Long> {
    
    // 기본 CRUD 메서드는 JpaRepository에서 자동으로 제공됩니다.

    // 성별로 사용자 찾기
    List<UserMatching> findByGender(String gender);

    // 키와 몸무게 범위로 사용자 찾기
    List<UserMatching> findByHeightBetweenAndWeightBetween(
        double minHeight, double maxHeight, double minWeight, double maxWeight);

    // IPF GL 점수 범위로 사용자 찾기 (이 메서드는 실제로 구현이 필요할 수 있습니다)
    // List<UserMatching> findByIPFGLPointsBetween(double minPoints, double maxPoints);

    // 이름으로 사용자 찾기 (부분 일치)
    List<UserMatching> findByNameContaining(String name);
}