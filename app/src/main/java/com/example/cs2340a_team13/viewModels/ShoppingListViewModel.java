package com.example.cs2340a_team13.viewModels;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;

public class ShoppingListViewModel {
    private static ShoppingListViewModel instance;
    private User user = UserViewModel.getInstance().getUser();
    private final DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
    private static int pantryQuantity;

    private ShoppingListViewModel() { }

    public static ShoppingListViewModel getInstance() {
        if (instance == null) {
            instance = new ShoppingListViewModel();
        }
        return instance;
    }

    private Ingredient getExistingIngredient(String ingredientName) {
        for (Ingredient ingredient : user.getShoppingList()) {
            if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                return ingredient;
            }
        }
        return null;
    }
    private Ingredient checkIfIngredientExistsInPantry(String ingredientName) {
        for (Ingredient ingredient : user.getPantryIngredients()) {
            if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                return ingredient; // Ingredient found in pantry
            }
        }
        return null; // Ingredient not found in pantry
    }


    public void addToShoppingList(String ingredientName, int quantity) {
        // create temp ingredient obj to test if its in SL
        Ingredient existingIngredient = getExistingIngredient(ingredientName);
        //aq
        int adjustedQuantity = quantity;
        // if its in shopping list
        if (existingIngredient != null) {
            existingIngredient.setQuantity(existingIngredient.getQuantity() + quantity);
            //increase quantity
        }
        // create temp ingredient obj to test in pantry
        Ingredient ingredientExistsInPantry = checkIfIngredientExistsInPantry(ingredientName);
        // if its in pantry, get pantry quantity and subtract it from inputted quantity
        if (ingredientExistsInPantry != null) {
            pantryQuantity = 0;
            for (Ingredient ingredient : user.getPantryIngredients()) {
                if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                    pantryQuantity = ingredient.getQuantity();
                    break;
                }
            }
        }

        adjustedQuantity = quantity - pantryQuantity;
        if (adjustedQuantity <= 0) {
            // if quantity below 0 and in shopping list, remove and return
            // if quantity below 0 and not in shopping list, return method
            if (existingIngredient != null) {
                user.getShoppingList().remove(existingIngredient);
            }
            return;
        }
        Ingredient newIngredient = null;
        if (existingIngredient != null) {
            existingIngredient.setQuantity(adjustedQuantity);
        } else {
            newIngredient = new Ingredient(ingredientName, adjustedQuantity, 0, "");
            user.getShoppingList().add(newIngredient);
        }


        // Update shopping list in the database
        databaseAccess.updateShoppingList(user.getUsername(), user.getShoppingList(), (DatabaseAccess.PantryCallback) ingredients -> {
        });
    }
    }


