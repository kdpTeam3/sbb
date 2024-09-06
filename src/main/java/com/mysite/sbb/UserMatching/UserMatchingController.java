package com.mysite.sbb.UserMatching;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/matching")
public class UserMatchingController {

    @Autowired
    private UserMatchingService service;

    @GetMapping
    public String showMatchingPage(Model model) {
        model.addAttribute("user", new UserMatching());
        return "userMatchingPage";
    }

    @PostMapping("/find")
    @ResponseBody
    public List<UserMatching> findMatches(@RequestBody UserMatching targetUser) {
        return service.findMatches(targetUser, 5);
    }
}