package com.mysite.sbb.UserMatching;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserMatchingController {

    @GetMapping("/user-matching")
    public String showMatchingPage() {
        return "UserMatchingPage"; // UserMatchingPage.html을 렌더링합니다.
    }
}
