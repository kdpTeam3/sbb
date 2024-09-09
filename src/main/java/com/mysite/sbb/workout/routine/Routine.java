package com.mysite.sbb.workout.routine;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "body_part")
    private String bodyPart;

    @Column(name = "sets")
    private Integer sets;

    @Column(name = "reps")
    private Integer reps;

    @Column(name = "weight")
    private Integer weight;

    public Routine(){}

    public Routine(String dayOfWeek, String bodyPart, Integer sets, Integer reps, Integer weight){
        this.dayOfWeek = dayOfWeek;
        this.bodyPart = bodyPart;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }
}
