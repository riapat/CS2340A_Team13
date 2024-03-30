package com.example.cs2340a_team13.viewModels;
import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;

import java.util.List;

public class IngredientViewModel {
    private UserViewModel userViewModel = UserViewModel.getInstance();
    private Ingredient ingredient = null;
    private User currentUser = userViewModel.getUser(); // Reference to current user
    private static IngredientViewModel instance;
    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    public static synchronized IngredientViewModel getInstance() {
        if (instance == null) {
            instance = new IngredientViewModel();
        }
        return instance;
    }
    public Ingredient createIngredient(String ingredientName, int quantity, int calories, String expirationDate){
        if (ingredient == null){
            ingredient = new Ingredient();
        }
        ingredient.setIngredientName(ingredientName);
        ingredient.setCalories(calories);
        ingredient.setQuantity(quantity);
        ingredient.setExpirationDate(expirationDate);
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


