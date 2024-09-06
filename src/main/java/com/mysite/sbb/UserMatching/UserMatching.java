package com.mysite.sbb.UserMatching;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserMatching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double height;
    private double weight;
    private String gender;
    private double benchpress;
    private double squat;
    private double deadlift;

    // Constructors, getters, and setters

    public double getTotalWeight() {
        return benchpress + squat + deadlift;
    }

    public double getIPFGLPoints() {
        double total = getTotalWeight();
        double a, b, c;

        if ("M".equals(gender)) {
            a = 1199.72839;
            b = 1025.18162;
            c = 0.00921;
        } else {
            a = 610.32796;
            b = 1045.59282;
            c = 0.03048;
        }

        double ipfGLPoint = 100 * total / (a - b * Math.exp(-c * weight));
        BigDecimal bd = new BigDecimal(Math.max(0, ipfGLPoint)).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}