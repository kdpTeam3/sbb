package com.mysite.sbb.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


// extends JpaRepository<Question, Integer> 
// JpaRepository 를 extends 상속을 하고 어떤 엔티티 클래스인지 엔티티에서 사용하는 ID가 어떤 타입인지
// => 반드시 클래스 형태로 작성해야 됨 <Question, Integer>
public interface QuestionRepository extends JpaRepository<Question, Integer>{
	
	// findBySubject 매개변수로 제목값을 넘겨줌 
	// return을 Question으로 받고 있음
	
	// findBySubject는 마음대로 사용하는게 아니라 jpa에서 규칙을 정해놨음
	// findBySubject 라고 작성을 하면 쿼리를 대신한다!
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	Page<Question> findAll(Pageable pageable);

}
