package com.mysite.sbb.workout.bodypart;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BodyPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chest")
    private String chest;

    @Column(name = "triceps")
    private String triceps;

    @Column(name = "back")
    private String back;

    @Column(name = "biceps")
    private String biceps;

    @Column(name = "leg")
    private String leg;

    @Column(name = "shoulder")
    private String shoulder;

    @Column(name = "abs")
    private String abs;

    public BodyPart(){}

    public BodyPart(String chest, String triceps, String back, String biceps, String leg, String shoulder, String abs){
        this.chest = chest;
        this.triceps = triceps;
        this.back = back;
        this.biceps = biceps;
        this.leg = leg;
        this.shoulder = shoulder;
        this.abs = abs;
    }




}
