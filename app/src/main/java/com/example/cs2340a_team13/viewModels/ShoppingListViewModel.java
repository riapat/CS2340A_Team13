package com.example.cs2340a_team13.viewModels;
import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;

import java.util.List;

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
    public void addToShoppingList(String ingredientName, int quantity) {
        // First, check if the ingredient already exists in the shopping list
        boolean ingredientExists = false;
        for (Ingredient ingredient : user.getShoppingList()) {
            if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                // Ingredient exists in shopping list, update quantity
                ingredient.setQuantity(ingredient.getQuantity() + quantity);
                ingredientExists = true;
                break;
            }
        }

        // If the ingredient doesn't exist, add it to the shopping list
        if (!ingredientExists) {
            Ingredient newIngredient = new Ingredient(ingredientName, quantity, 0, "");
            user.getShoppingList().add(newIngredient);

            // Update shopping list in the database
            databaseAccess.updateShoppingList(user.getUsername(), user.getShoppingList(), (DatabaseAccess.PantryCallback) ingredients -> {
            });
        }
    }
    public void adjustShoppingListWithPantry(String ingredientName, int pantryQuantity) {
        // Find the ingredient in the shopping list
        for (Ingredient ingredient : user.getShoppingList()) {
            if (ingredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                int shoppingListQuantity = ingredient.getQuantity();
                int adjustedQuantity = shoppingListQuantity - pantryQuantity;

                // Update quantity in shopping list
                if (adjustedQuantity <= 0) {
                    // Remove from shopping list if adjusted quantity is zero or negative
                    user.getShoppingList().remove(ingredient);

                    // Update shopping list in the database
                    databaseAccess.updateShoppingList(user.getUsername(), user.getShoppingList(), new DatabaseAccess.PantryCallback() {
                    });
                } else {
                    // Update quantity in shopping list
                    ingredient.setQuantity(adjustedQuantity);

                    // Update shopping list in the database
                    databaseAccess.updateShoppingList(user.getUsername(), user.getShoppingList(), new DatabaseAccess.PantryCallback() {
                    });
                }
                break;
            }
        }
    }
}