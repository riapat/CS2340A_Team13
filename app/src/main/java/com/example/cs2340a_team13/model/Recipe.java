package com.example.cs2340a_team13.model;

import java.util.HashMap;
public class Recipe {
    private String recipeName;
    private HashMap<Ingredient,Integer> ingredients;
    private int caloriesPerServing;
    private int cookingTime;

    public Recipe(String recipeName, HashMap<Ingredient, Integer> ingredients){
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.caloriesPerServing = calculateCaloriesPerServing();
        this.cookingTime = 0;

    }
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public HashMap<Ingredient, Integer> getIngredients(){
        return ingredients;
    }



    public int calculateCaloriesPerServing() {
        int caloriesPerServing = 0;
        for(Ingredient item:ingredients.keySet()){
            caloriesPerServing += item.getCalories();
        }
        return caloriesPerServing;
    }

    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }
    public void setCaloriesPerServing(int caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }
}