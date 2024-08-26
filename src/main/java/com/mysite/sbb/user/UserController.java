package com.mysite.sbb.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // Lombok을 사용하여 final 필드에 대해 자동으로 생성자를 생성합니다.
@Controller  // 이 클래스가 Spring MVC의 컨트롤러임을 나타냅니다.
@RequestMapping("/user")  // 이 컨트롤러의 요청 경로가 "/user"로 시작됨을 정의합니다.
public class UserController {

    private final UserService userService;  // UserService를 의존성 주입을 통해 사용합니다.
    
    @GetMapping("/signup")  // "/user/signup" 경로에 대한 GET 요청을 처리합니다.
    public String signup(UserCreateForm userCreateForm) {
        // 회원가입 폼을 보여주는 메서드입니다.
        return "signup_form";  // 회원가입 폼 뷰를 반환합니다.
    }
    
    @PostMapping("/signup")  // "/user/signup" 경로에 대한 POST 요청을 처리합니다.
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        // 회원가입 폼에서 제출된 데이터를 처리합니다.
        
        if (bindingResult.hasErrors()) {
            // 폼에서 유효성 검사를 통과하지 못한 경우, 다시 폼을 보여줍니다.
            return "signup_form";
        }
        
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            // 비밀번호와 비밀번호 확인이 일치하지 않는 경우, 에러 메시지를 추가하고 다시 폼을 보여줍니다.
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }
        
        try {
            // 새로운 사용자를 생성하고 데이터베이스에 저장합니다.
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            // 데이터베이스에서 제약 조건(예: 중복된 사용자 이름이나 이메일)을 위반한 경우 처리합니다.
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            // 기타 예외 발생 시 처리합니다.
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        
        return "redirect:/";  // 회원가입이 성공적으로 완료되면 메인 페이지로 리다이렉트합니다.

    }
    
    	@GetMapping("/login")
    	
    		public String login() {
    		return "login_form";
    }
}

















