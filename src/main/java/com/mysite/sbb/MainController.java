package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


// @Controller : 이 클래스는 Controller로 사용하겠다 라고 선언하게 됨
@Controller

public class MainController {
	
	//@GetMapping 이라는 애너테이션 주소 요청을 받아올 수 있도록 ex)/idex
	@GetMapping("/sbb")
	
	// @ResponBody :  URL 요청에 대한 응답 그래서 void = Stirng으로 교체 
	// index라는 문자열을 브라우저에 출력하기 위해 index 메서드의 리턴 자료형을 String으로 변경하고 문자열
	// 'index'를 리턴했다. 여기서 @ResponBody 애너테이션은 URL 요청에 대한 응답으로 문자열을 리턴하는 의미로 쓰였다
	@ResponseBody
	public String index() {
		return "키키키키익";
	}
	
	@GetMapping("/")
	public String root() {
		return "redirect:/question/list"; // question/list 이 부분이 리다이렉트할 URL
		// redirect : return이 오면 다시 /question/list 다시 재요청
		// 컨트롤러 메서드에서 다른 컨트롤러 메서드 (특정 주소)를 부르고 싶다 하면  redirect룰 사용한다
	}

	

}
