package com.example.cs2340a_team13.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Meal> loggedMeals;
    private int height;
    private int weight;
    private int age;
    private String gender;
    private List<Ingredient> pantryIngredients;
    private List<Ingredient> shoppingList;

    public User() {
        this.username = "";
        this.password = "";
        loggedMeals = new ArrayList<>();
        pantryIngredients = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        loggedMeals = new ArrayList<>();
        pantryIngredients = new ArrayList<>();
        this.height = 0;
        this.weight = 0;
        this.age = 0;
        this.gender = "";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
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

    public List<Ingredient> getPantryIngredients() {
        return pantryIngredients;
    }

    public void setPantryIngredients(List<Ingredient> pantryIngredient) {
        this.pantryIngredients = pantryIngredient;
    }

    public void addPantryIngredient(Ingredient ingredient) {
        pantryIngredients.add(ingredient);
    }

    public void addMeal(Meal meal) {
        loggedMeals.add(meal);
    }

    public void clearLoggedMeals() {
        loggedMeals.clear();
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Ingredient> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<Ingredient> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public void setUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.loggedMeals = user.getMeals();
    }
}
