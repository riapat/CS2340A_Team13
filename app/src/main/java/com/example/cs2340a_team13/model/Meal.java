package com.example.cs2340a_team13.model;

public class Meal {
    // Attributes
    private String name;
    private int calorieCount;

    // Constructors
    public Meal() {
        this.name = "";
        this.calorieCount = 0;
    }

    public Meal(String name, int calorieCount) {
        this.name = name;
        this.calorieCount = calorieCount;
    }

    // Getters and setters
    public String getName() {
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

}
