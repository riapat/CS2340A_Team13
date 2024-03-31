package com.example.cs2340a_team13.model;
import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String recipeName;
    private String recipeDescription;
    private List<Ingredient> recipeIngredients;
    private String recipeInstructions;
    private int caloriesPerServing;
    private int cookingTime;

    public Recipe() {
        this.recipeName = "";
        this.recipeDescription = "";
        this.recipeIngredients = null;
        this.recipeInstructions = "";
    }

    public Recipe(String recipeName, String recipeDescription,
                  List<Ingredient> recipeIngredients, String recipeInstructions) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.recipeIngredients = recipeIngredients;
        this.recipeInstructions = recipeInstructions;
        this.caloriesPerServing = calculateCaloriesPerServing();
        this.cookingTime = 0;
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

    public void setRecipe(Recipe recipe) {
        this.recipeName = recipe.getRecipeName();
        this.recipeDescription = recipe.getRecipeDescription();
        this.recipeIngredients = recipe.getRecipeIngredients();
        this.recipeInstructions = recipe.getRecipeInstructions();
    }

    public void addIngredient(Ingredient ingredient) {

        if (recipeIngredients == null) {
            recipeIngredients = new ArrayList<Ingredient>();
        }
        recipeIngredients.add(ingredient);
    }
    public int calculateCaloriesPerServing() {
        int caloriesPerServing = 0;
        if(recipeIngredients==null){
            return 0;
        } else if(recipeIngredients.isEmpty()){
            return 0;
        }
        for (Ingredient item:recipeIngredients) {
                caloriesPerServing += item.getCalories() * item.getQuantity();
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
    public boolean isIngredientsEnough(List<Ingredient> pantry, List<Ingredient> recipeIngredients) {
        for (Ingredient requiredIngredient : recipeIngredients) {
            boolean found = false; // Flag to check if the required ingredient is found in the pantry

            for (Ingredient pantryIngredient : pantry) {
                if (requiredIngredient.getIngredientName().equals(pantryIngredient.getIngredientName())) {
                    found = true; // The ingredient is found
                    if (pantryIngredient.getQuantity() < requiredIngredient.getQuantity()) {
                        return false; // Found but not enough quantity
                    }
                    break; // No need to continue checking this ingredient
                }
            }

            if (!found) {
                return false; // Required ingredient is not in the pantry
            }
        }
        return true; // All required ingredients are found in sufficient quantities
    }


}