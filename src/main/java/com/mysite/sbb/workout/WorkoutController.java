package com.mysite.sbb.workout;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import com.mysite.sbb.workout.routine.Routine;
import com.mysite.sbb.workout.routine.RoutineRepository;
import com.mysite.sbb.workout.routine.RoutineUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/workout")
@Controller
public class WorkoutController {


    private final RoutineRepository routineRepository;
    private final UserService userService;

    public WorkoutController(RoutineRepository routineRepository, UserService userService) {
        this.routineRepository = routineRepository;
        this.userService = userService;
    }

    // 운동 가이드
    @GetMapping("/guide")
    public String Guide() {
        return "workout_guide";
    }

    @GetMapping("/guide/chest")
    public String Chest() {
        return "workout_content/chest";
    }

    @GetMapping("/guide/triceps")
    public String Triceps() {
        return "workout_content/triceps";
    }

    @GetMapping("/guide/back")
    public String Back() {
        return "workout_content/back";
    }

    @GetMapping("/guide/biceps")
    public String Biceps() {
        return "workout_content/biceps";
    }

    @GetMapping("/guide/leg")
    public String Leg() {
        return "workout_content/leg";
    }

    @GetMapping("/guide/shoulder")
    public String Shoulder() {
        return "workout_content/shoulder";
    }

    @GetMapping("/guide/abs")
    public String Abs() {
        return "workout_content/abs";
    }

    // 루틴 만들기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/make_routine")
    public String Make_routine(Model model) {
        model.addAttribute("routineUpdateDto", new RoutineUpdateDto());
        return "make_routine";
    }

    @PostMapping("/make_routine")
    public String handleFormSubmission(@ModelAttribute RoutineUpdateDto routineUpdateDto, Model model, Principal principal) {
        // 현재 로그인한 사용자 가져오기
        SiteUser siteUser = userService.findByUsername(principal.getName());

        // DTO를 엔티티로 변환
        Routine routineEntity = new Routine();
        routineEntity.setDayOfWeek(routineUpdateDto.getDayOfWeek());
        routineEntity.setBodyPart(routineUpdateDto.getBodyPart());
        routineEntity.setSets(routineUpdateDto.getSets());
        routineEntity.setReps(routineUpdateDto.getReps());
        routineEntity.setWeight(routineUpdateDto.getWeight());
        routineEntity.setSiteUser(siteUser);


        // 데이터 저장
        routineRepository.save(routineEntity);

        model.addAttribute("message", "성공적으로 제출!");
        model.addAttribute("routine", routineUpdateDto);

        return "make_routine";
    }


}
