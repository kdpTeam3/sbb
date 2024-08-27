package com.mysite.sbb.user;

import java.security.Principal;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String userModifyForm(UserModifyForm userModifyForm, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        userModifyForm.setUsername(siteUser.getUsername());
        userModifyForm.setEmail(siteUser.getEmail());
        return "user_modify_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String userModify(@Valid UserModifyForm userModifyForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "user_modify_form";
        }

        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.userService.modify(siteUser, userModifyForm.getUsername(), userModifyForm.getEmail());
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete")
    public String userDeleteForm() {
        return "user_delete_confirm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete")
    public String userDelete(Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.userService.delete(siteUser);
        return "redirect:/user/logout";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/info")
    public String userInfo(Model model, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        model.addAttribute("user", siteUser);
        return "user_info";
    }
}
