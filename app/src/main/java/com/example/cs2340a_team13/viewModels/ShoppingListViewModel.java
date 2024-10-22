package com.example.cs2340a_team13.viewModels;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.UserObserver;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;

import java.util.List;

public class ShoppingListViewModel implements UserObserver {
    private static ShoppingListViewModel instance;
    private User user = UserViewModel.getInstance().getUser();
    private static int pantryQuantity = 0;

    private ShoppingListViewModel() {
        UserViewModel.getInstance().registerObserver(this);
    }


    public static ShoppingListViewModel getInstance() {
        if (instance == null) {
            instance = new ShoppingListViewModel();
        }
        return instance;
    }

    public Ingredient getExistingIngredient(String ingredientName) {
        for (Ingredient ingredient : user.getShoppingList()) {
            if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                return ingredient;
            }
        }
        return null;
    }
    public Ingredient checkIfIngredientExistsInPantry(String ingredientName) {
        for (Ingredient ingredient : user.getPantryIngredients()) {
            if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                return ingredient; // Ingredient found in pantry
            }
        }
        return null; // Ingredient not found in pantry
    }


    public void addToShoppingList(String ingredientName, int quantity, int calories) {
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
                            calories, "");
                    List<Ingredient> currentsl = user.getShoppingList();
                    currentsl.add(newIngredient);
                    user.setShoppingList(currentsl);
                    // now have to update SL
                }
            } else {
                Ingredient newIngredient = new Ingredient(ingredientName, quantity, calories,
                        "");
                List<Ingredient> currentsl = user.getShoppingList();
                currentsl.add(newIngredient);
                user.setShoppingList(currentsl);
            }
        }
        // Update shopping list in the database
        DatabaseAccess.getInstance().updateShoppingList(user.getUsername(), user.getShoppingList(),
                (DatabaseAccess.PantryCallback) ingredients -> { });
    }

    // In ShoppingListViewModel (add this method for testing purpose)
    public User getTestUser() {
        return this.user;
    }

    @Override
    public void updateUser(User user) {
        this.user = user;
    }
}


