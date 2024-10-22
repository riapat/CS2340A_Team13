package com.example.cs2340a_team13.viewModels;

import android.util.Log;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;

import java.util.ArrayList;
import java.util.List;

public class IngredientViewModel {
    private static IngredientViewModel ingredientInstance;

    private UserViewModel userViewModel = UserViewModel.getInstance();
    private User currentUser = userViewModel.getUser(); // Reference to current user

    // Private constructor to prevent instantiation outside of this class
    private IngredientViewModel() { }

    // Singleton instance getter
    public static IngredientViewModel getInstance() {
        if (ingredientInstance == null) {
            ingredientInstance = new IngredientViewModel();
        }
        return ingredientInstance;
    }

    public Ingredient createIngredient(String ingredientName, int quantity,
                                       int calories, String expirationDate) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(ingredientName);
        ingredient.setCalories(calories);
        ingredient.setQuantity(quantity);
        ingredient.setExpirationDate(expirationDate);

        if (currentUser != null) {
            List<Ingredient> pantry = currentUser.getPantryIngredients();
            if (pantry != null) {
                pantry.add(ingredient);
            } else {
                pantry = new ArrayList<>();
                pantry.add(ingredient);
                currentUser.setPantryIngredients(pantry);
            }
        }

        DatabaseAccess.getInstance().addToPantry(currentUser, ingredient, userCallback -> {
            if (userCallback != null) {
                System.out.println("Ingredient added to pantry");
            } else {
                System.out.println("Ingredient not added to pantry");
            }
        });

        return ingredient;
    }

    public boolean checkDuplicate(Ingredient ingredient) {
        if (currentUser != null && currentUser.getPantryIngredients() != null) {
            for (Ingredient pantryIngredient : currentUser.getPantryIngredients()) {
                if (pantryIngredient.getIngredientName().equals(ingredient.getIngredientName())
                        &&
                        pantryIngredient.getQuantity() > 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean existingIngredient(String ingredientName) {
        Log.e("IngredientViewModel", currentUser == null ? "User is not loaded" : "User is loaded");
        Log.d("IngredientViewModel", "Pantry size: " + currentUser.getPantryIngredients().size());
        if (currentUser != null && currentUser.getPantryIngredients() != null) {
            for (Ingredient pantryIngredient : currentUser.getPantryIngredients()) {
                if (pantryIngredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void increaseIngredient(String ingredientName, int quantityAdded) {
        Log.e("IngredientViewModel", currentUser == null ? "User is not loaded" : "User is loaded");
        if (currentUser != null && currentUser.getPantryIngredients() != null) {
            for (Ingredient pantryIngredient : currentUser.getPantryIngredients()) {
                System.out.println(pantryIngredient.getIngredientName() + " " + ingredientName);
                if (pantryIngredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                    int currentQuantity = pantryIngredient.getQuantity();
                    int newQuantity = currentQuantity + quantityAdded;
                    pantryIngredient.setQuantity(newQuantity);

                    DatabaseAccess.getInstance().updateToUserDB(currentUser, userCallback -> {
                    });
                    return;
                }
            }
        }
    }

    public void decreaseIngredient(String ingredientName, int quantityAdded) {
        if (currentUser != null && currentUser.getPantryIngredients() != null) {
            for (Ingredient pantryIngredient : currentUser.getPantryIngredients()) {
                System.out.println(pantryIngredient.getIngredientName() + " " + ingredientName);
                if (pantryIngredient.getIngredientName().equalsIgnoreCase(ingredientName)) {
                    int currentQuantity = pantryIngredient.getQuantity();
                    if (quantityAdded >= currentQuantity) { //remove whole ingredient
                        List<Ingredient> updatedIngredients = currentUser.getPantryIngredients();
                        updatedIngredients.remove(pantryIngredient);
                        currentUser.setPantryIngredients(updatedIngredients);
                        break;
                    } else {
                        int newQuantity = currentQuantity - quantityAdded;
                        pantryIngredient.setQuantity(newQuantity);
                        break;
                    }
                }
            }
            DatabaseAccess.getInstance().updateToUserDB(currentUser, userCallback -> {
                if (userCallback != null) {
                    System.out.println("Pantry Updated from decreaseIngredient");
                } else {
                    System.out.println("Pantry Not Updated from decreaseIngredient");
                }
            });
        }
    }
    public void addIngredients(List<Ingredient> ingredients) {
        String ingredientName;
        int quantity;
        int calories;
        String expirationDate;
        for (Ingredient ingredient : ingredients) {
            ingredientName = ingredient.getIngredientName();
            quantity = ingredient.getQuantity();
            calories = ingredient.getCalories();
            expirationDate = ingredient.getExpirationDate();
            if (existingIngredient(ingredientName)) {
                increaseIngredient(ingredientName, quantity);
            } else {
                createIngredient(ingredientName, quantity, calories, expirationDate);
            }
        }
        DatabaseAccess.getInstance().updateShoppingList(currentUser.getUsername(),
                currentUser.getShoppingList(),
                (DatabaseAccess.PantryCallback) ingredients1 -> { });
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    public User getCurrentUser() {
        return currentUser;

    }
}