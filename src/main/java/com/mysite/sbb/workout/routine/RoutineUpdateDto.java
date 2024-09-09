package com.mysite.sbb.workout.routine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoutineUpdateDto {
    private String dayOfWeek;
    private String bodyPart;
    private Integer sets;
    private Integer reps;
    private Integer weight;

    public RoutineUpdateDto(){
    }

    public RoutineUpdateDto(String dayOfWeek, String bodyPart, Integer sets, Integer reps, Integer weight) {
        this.dayOfWeek = dayOfWeek;
        this.bodyPart = bodyPart;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }
}
