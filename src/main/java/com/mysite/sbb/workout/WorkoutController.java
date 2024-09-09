package com.mysite.sbb.workout;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/workout")
@Controller
public class WorkoutController {



    // 운동 가이드    
    @GetMapping("/guide")
    public String Guide(){
        return "workout_guide";
    }

    @GetMapping("/guide/chest")
    public String Chest(){
        return"workout_content/chest";
    }
    @GetMapping("/guide/triceps")
    public String Triceps(){
        return"workout_content/triceps";
    }
    @GetMapping("/guide/back")
    public String Back(){
        return"workout_content/back";
    }
    @GetMapping("/guide/biceps")
    public String Biceps(){
        return"workout_content/biceps";
    }
    @GetMapping("/guide/leg")
    public String Leg(){
        return"workout_content/leg";
    }
    @GetMapping("/guide/shoulder")
    public String Shoulder(){
        return"workout_content/shoulder";
    }
    @GetMapping("/guide/abs")
    public String Abs(){
        return"workout_content/abs";
    }
    
    // 루틴 만들기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/make_routine")
    public String Make_routine(){
        return "make_routine";
    }

}
