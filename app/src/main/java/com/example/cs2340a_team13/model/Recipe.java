package com.example.cs2340a_team13.model;

import java.util.List;

public class Recipe {
private String recipeName;
    private String recipeDescription;
    private List<Ingredient> recipeIngredients;
    private String recipeInstructions;

    public Recipe() {
        this.recipeName = "";
        this.recipeDescription = "";
        this.recipeIngredients = null;
        this.recipeInstructions = "";
    }

    public Recipe(String recipeName, String recipeDescription, List<Ingredient> recipeIngredients, String recipeInstructions) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.recipeIngredients = recipeIngredients;
        this.recipeInstructions = recipeInstructions;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public List<Ingredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<Ingredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }
}
