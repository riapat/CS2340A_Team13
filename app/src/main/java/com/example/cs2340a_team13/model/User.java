package com.example.cs2340a_team13.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private List<Meal> loggedMeals;

    public User() {
        this.username = "";
        this.password = "";
        loggedMeals = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        loggedMeals = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Meal> getMeals() {
        return loggedMeals;
    }

    public void addMeal(Meal meal) {
        loggedMeals.add(meal);
    }

    public void clearLoggedMeals() {
        loggedMeals.clear();
    }

    public void setUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
