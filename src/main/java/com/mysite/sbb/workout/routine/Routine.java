package com.mysite.sbb.workout.routine;

import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략 설정
    private Long id; // Long 타입의 자동 생성 ID

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

    @ManyToOne
    @JoinColumn(name = "username",referencedColumnName = "username") // 외래 키로 사용
    private SiteUser siteUser;

    public Routine() {
    }

    public Routine(String dayOfWeek, String bodyPart, Integer sets, Integer reps, Integer weight, SiteUser siteUser) {

        this.dayOfWeek = dayOfWeek;
        this.bodyPart = bodyPart;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.siteUser = siteUser;

    }

}
