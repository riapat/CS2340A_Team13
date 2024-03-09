package com.example.cs2340a_team13.model;

import java.util.Date;


import java.util.Date;

public class Meal {
    // Attributes
    private String name;
    private int calorieCount;
    private Date date; // Adding a date attribute

    // Constructors
    public Meal() {
        this.name = "";
        this.calorieCount = 0;
        this.date = new Date(); // Default to current date
    }

    public Meal(String name, int calorieCount) {
        this.name = name;
        this.calorieCount = calorieCount;
        this.date = new Date(); // Default to current date
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setMeal(String name) {
        this.name = name;
    }

    public int getCalorieCount() {
        return calorieCount;
    }

    public void updateMeal(String name, int calorieCount) {
        this.name = name;
        this.calorieCount = calorieCount;
    }

    public void setCalorieCount(int calorieCount) {
        this.calorieCount = calorieCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}