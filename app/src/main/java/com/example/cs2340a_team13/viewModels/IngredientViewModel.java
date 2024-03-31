package com.example.cs2340a_team13.viewModels;
import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;

import java.util.ArrayList;
import java.util.List;

public class IngredientViewModel {
    private UserViewModel userViewModel = UserViewModel.getInstance();

    private User currentUser = userViewModel.getUser(); // Reference to current user

    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    public Ingredient createIngredient(String ingredientName, int quantity, int calories, String expirationDate){
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(ingredientName);
        ingredient.setCalories(calories);
        ingredient.setQuantity(quantity);
        ingredient.setExpirationDate(expirationDate);


        if (currentUser != null) {

            List<Ingredient> pantry = currentUser.getPantry();
            if (pantry != null) {
                pantry.add(ingredient);
            } else {
                pantry = new ArrayList<>();
                pantry.add(ingredient);
                currentUser.setPantry(pantry);
            }
        }

        databaseAccess.addToPantry(currentUser, ingredient, userCallback -> {
            if (userCallback != null) {
                System.out.println("Ingredient added to pantry");
            } else {
                System.out.println("Ingredient not added to pantry");
            }
        });

        return ingredient;
    }
    }


