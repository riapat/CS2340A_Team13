package com.example.cs2340a_team13.viewModels;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;

public class ShoppingListViewModel {
    private static ShoppingListViewModel instance;
    private User user = UserViewModel.getInstance().getUser();
    private final DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

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

    private void increaseSLQuantity(Ingredient existingIngredient, int quantity) {
        // Increase quantity of the duplicate item
        existingIngredient.setQuantity(existingIngredient.getQuantity() + quantity);

        // Update shopping list in the database
        databaseAccess.updateShoppingList(user.getUsername(), user.getShoppingList(), (DatabaseAccess.PantryCallback) ingredients -> {
        });
    }

    private void adjustSLQuantityWithPantry(Ingredient existingIngredient, int quantity) {
        // Check if the ingredient exists in the pantry
        boolean ingredientExistsInPantry = checkIfIngredientExistsInPantry(existingIngredient.getIngredientName());

        // If the ingredient exists in the pantry, adjust the quantity in the shopping list
        if (ingredientExistsInPantry) {
            int pantryQuantity = getPantryQuantity(existingIngredient.getIngredientName());
            int adjustedQuantity = existingIngredient.getQuantity() - pantryQuantity;
            if (adjustedQuantity <= 0) {
                // Remove from shopping list if adjusted quantity is zero or negative
                user.getShoppingList().remove(existingIngredient);
            } else {
                // Update quantity in shopping list
                existingIngredient.setQuantity(adjustedQuantity);
            }

            // Update shopping list in the database
            databaseAccess.updateShoppingList(user.getUsername(), user.getShoppingList(), (DatabaseAccess.PantryCallback) ingredients -> {
            });
        } else {
            // If the ingredient doesn't exist in the pantry, increase its quantity in the shopping list
            increaseSLQuantity(existingIngredient, quantity);
        }
    }

    private boolean checkIfIngredientExistsInPantry(String ingredientName) {
        for (Ingredient ingredient : user.getPantryIngredients()) {
            if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                return true; // Ingredient found in pantry
            }
        }
        return false; // Ingredient not found in pantry
    }

    private int getPantryQuantity(String ingredientName) {
        for (Ingredient ingredient : user.getPantryIngredients()) {
            if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                return ingredient.getQuantity();
            }
        }
        return 0; // Ingredient not found in pantry
    }

    public void addToShoppingList(String ingredientName, int quantity) {
        Ingredient existingIngredient = getExistingIngredient(ingredientName);
        if (existingIngredient != null) {
            adjustSLQuantityWithPantry(existingIngredient, quantity);
        } else {
            Ingredient newIngredient = new Ingredient(ingredientName, quantity, 0, "");
            user.getShoppingList().add(newIngredient);
            user.setShoppingList(user.getShoppingList());

            // Update shopping list in the database
            databaseAccess.updateShoppingList(user.getUsername(), user.getShoppingList(), (DatabaseAccess.PantryCallback) ingredients -> {
            });
        }
    }
}
