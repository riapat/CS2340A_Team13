package com.example.cs2340a_team13.viewModels;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;

import java.util.List;

public class ShoppingListViewModel {
    private static ShoppingListViewModel instance;
    private User user = UserViewModel.getInstance().getUser();
    private final DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
    private static int pantryQuantity = 0;

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
        // create temp ingredient obj to test if it's in SL
        Ingredient existingIngredient = getExistingIngredient(ingredientName);
        //aq
        int adjustedQuantity = quantity;
        // if it's in the shopping list
        if (existingIngredient != null) {
            List<Ingredient> currentsl = user.getShoppingList();
            for (Ingredient ingredient : currentsl) {
                if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                    ingredient.setQuantity(existingIngredient.getQuantity() + quantity);
                    user.setShoppingList(currentsl);
                    break;
                }
            }
            //increase quantity
        } else {
            // create temp ingredient obj to test in pantry
            Ingredient ingredientExistsInPantry = checkIfIngredientExistsInPantry(ingredientName);
            // if it's in the pantry, get pantry quantity and subtract it from the inputted quantity
            if (ingredientExistsInPantry != null) {
                pantryQuantity = ingredientExistsInPantry.getQuantity();
                adjustedQuantity = quantity - pantryQuantity;
                if (adjustedQuantity <= 0) {
                    return;
                } else {
                    Ingredient newIngredient = new Ingredient(ingredientName, adjustedQuantity,
                            0, "");
                    List<Ingredient> currentsl = user.getShoppingList();
                    currentsl.add(newIngredient);
                    user.setShoppingList(currentsl);
                    // now have to update SL
                }
            } else {
                Ingredient newIngredient = new Ingredient(ingredientName, quantity, 0,
                        "");
                List<Ingredient> currentsl = user.getShoppingList();
                currentsl.add(newIngredient);
                user.setShoppingList(currentsl);
            }
        }
        // Update shopping list in the database
        databaseAccess.updateShoppingList(user.getUsername(), user.getShoppingList(),
                (DatabaseAccess.PantryCallback) ingredients -> { });
    }
}


