package com.mysite.sbb.workout.routine;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/routine")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

//    @GetMapping("/make_routine")
//    public String showForm(Model model) {
//        model.addAttribute("routineUpdateDto", new RoutineUpdateDto());
//        return "make_routine"; // 템플릿 이름
//    }
//
//    @PostMapping("/make_routine")
//    public String handleFormSubmission(@ModelAttribute RoutineUpdateDto routineUpdateDto, Model model) {
//        // 폼에서 제출된 데이터는 routineUpdateDto 객체에 바인딩
//        model.addAttribute("message", "성공적으로 제출!");
//        model.addAttribute("routine", routineUpdateDto);
//
//        return "make_routine";
//    }


}
