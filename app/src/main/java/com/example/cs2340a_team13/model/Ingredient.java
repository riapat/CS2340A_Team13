package com.example.cs2340a_team13.model;

public class Ingredient {
    private String ingredientName;
    private int quantity;
    private int calories;
    private String expirationDate;

    // Constructor with default values
    public Ingredient() {
        this("", 0, 0, "");
    }

    public Ingredient(String ingredientName, int quantity, int calories) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.calories = calories;
        this.expirationDate = "";
    }

    public Ingredient(String ingredientName, int quantity, int calories, String expirationDate) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.calories = calories;
        this.expirationDate = expirationDate;
    }
    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
