package com.mysite.sbb.workout.bodypart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyPartUpdateDto {
    private String chest;
    private String triceps;
    private String back;
    private String biceps;
    private String leg;
    private String shoulder;
    private String abs;

    public BodyPartUpdateDto() {
    }

    public BodyPartUpdateDto(String chest, String triceps, String back, String biceps, String leg, String shoulder, String abs) {
        this.chest = chest;
        this.triceps = triceps;
        this.back = back;
        this.biceps = biceps;
        this.leg = leg;
        this.shoulder = shoulder;
        this.abs = abs;

    }
}
