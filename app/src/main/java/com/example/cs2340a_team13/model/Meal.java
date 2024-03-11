package com.example.cs2340a_team13.model;

import java.util.Date;

public class Meal {
    // Attributes
    private String name;
    private int calorieCount;
    private Date date;

    // Constructors
    public Meal() {
        this.name = "";
        this.calorieCount = 0;
        this.date = new Date();
    }

    public Meal(String name, int calorieCount) {
        this.name = name;
        this.calorieCount = calorieCount;
        this.date = new Date();
    }

    // Getters and setters
    public String getMealName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalorieCount() {
        return calorieCount;
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

    // Method to update the whole Meal object
    public void setMeal(Meal meal) {
        this.name = meal.getMealName();
        this.calorieCount = meal.getCalorieCount();
        this.date = meal.getDate();
    }
}
