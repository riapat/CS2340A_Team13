package com.example.cs2340a_team13;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.Recipe;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.model.Meal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private FirebaseDatabase fbInstance;
    private static DatabaseAccess databaseInstance;

    private DatabaseAccess(FirebaseDatabase fbInstance) {
        this.fbInstance = fbInstance;
    }

    public static synchronized DatabaseAccess getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new DatabaseAccess(FirebaseDatabase.getInstance());
        }
        return databaseInstance;
    }



    //Write to "Users" table by sending in a User object
    //Has a callback function to turn this function to become synchronous
    public void writeToUserDB(User user, UserCallback callback) {
        fbInstance.getReference("Users").child(user.getUsername()).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("User added to user database");
                        callback.onComplete(user);
                    } else {
                        System.out.println("User not added to user database");
                        callback.onComplete(null);
                    }
                });
    }

    //Query from "Users" table by sending in a username
    public void readFromUserDB(String username, UserCallback callback) {
        User user = new User();
        DatabaseReference userRef = fbInstance.getReference("Users").child(username);
        userRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("DatabaseAccess",
                                "User found in user database "
                                        + task.getResult().getValue(User.class).getUsername());
                        user.setUser(task.getResult().getValue(User.class));

                        DatabaseReference mealRef = userRef.child("meals");
                        mealRef.get()
                                .addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        for (DataSnapshot mealSnapshot : task2
                                                .getResult().getChildren()) {
                                            Meal meal = mealSnapshot.getValue(Meal.class);
                                            if (meal != null) {
                                                user.addMeal(meal);
                                                Log.i("DatabaseAccess",
                                                        "Meal found in user database "
                                                                + mealSnapshot.getValue(Meal.class)
                                                                .getCalorieCount());
                                            }
                                        }
                                        callback.onComplete(user);
                                    } else {
                                        System.out.println("Meals not found in user database");
                                        callback.onComplete(null);
                                    }
                                });
                    } else {
                        System.out.println("User not found in user database");
                        callback.onComplete(null);
                    }
                });
    }

    //Update "Users" table by sending in a User object
    public void updateToUserDB(User user, UserCallback callback) {
        fbInstance.getReference("Users").child(user.getUsername()).setValue(user)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            System.out.println("User updated in user database");
                            callback.onComplete(user);
                        } else {
                            System.out.println("User not updated in user database");
                            callback.onComplete(null);
                        }
                    });
    }


    //Write to Meals table by sending in a Meal object
    public void writeToMealsDB(Meal meal, MealCallback callback) {
        fbInstance.getReference("Meals").child(meal.getMealName()).setValue(meal)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Meal added to meals database");
                        callback.onComplete(meal);
                    } else {
                        System.out.println("Meal not added to meals database");
                        callback.onComplete(null);
                    }
                });
    }

    //Query from Meals table by sending in a meal name
    public Meal readFromMealsDB(String mealName, MealCallback callback) {
        Meal meal = new Meal();
        fbInstance.getReference("Meals").child(mealName).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        meal.setMeal(task.getResult().getValue(Meal.class));
                        System.out.println("Meal found in meals database");
                        callback.onComplete(meal);
                    } else {
                        System.out.println("Meal not found in meals database");
                        callback.onComplete(null);
                    }
                });
        return meal;
    }

    //Update Meals table by sending in a Meal object
    public void updateToMealsDB(Meal meal, MealCallback callback) {
        fbInstance.getReference("Meals").child(meal.getMealName()).setValue(meal)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Meal updated in meals database");
                        callback.onComplete(meal);
                    } else {
                        System.out.println("Meal not updated in meals database");
                        callback.onComplete(null);
                    }
                });
    }

    //Add an Ingredient to Pantry list of the User by sending in an Ingredient object
    // and returns the User object with the updated pantry list to update the state of the User
    public void addToPantry(User user, Ingredient ingredient, UserCallback callback) {
        DatabaseReference pantryRef = fbInstance.getReference("Users")
                .child(user.getUsername()).child("pantry");

        pantryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Ingredient> ingredients = new ArrayList<>();

                // Retrieve existing ingredients and add them to the list
                for (DataSnapshot ingredientSnapshot : snapshot.getChildren()) {
                    Ingredient existingIngredient = ingredientSnapshot.getValue(Ingredient.class);
                    ingredients.add(existingIngredient);
                }

                // Add the new ingredient to the list
                ingredients.add(ingredient);

                // Set the entire list at once to the pantry reference
                pantryRef.setValue(ingredients).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Ingredient added to pantry");
                        callback.onComplete(user);
                    } else {
                        System.out.println("Ingredient not added to pantry");
                        callback.onComplete(null);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error adding ingredient to pantry: " + error.getMessage());
                callback.onComplete(null);
            }
        });
        // Append the ingredient to the pantry list
//        pantryRef.push().setValue(ingredient)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        System.out.println("Ingredient added to pantry");
//                        callback.onComplete(user);
//                    } else {
//                        System.out.println("Ingredient not added to pantry");
//                        callback.onComplete(null);
//                    }
//                });
    }

    //Load Pantry list of the User by sending in username
    //and return Pantry List of the User
    //Query the user and update the queried user
    public void loadPantry(String username, PantryCallback callback) {
        DatabaseReference pantryRef = fbInstance.getReference("Users")
                .child(username).child("pantry");
        pantryRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //Go through the pantry list and add each ingredient to the list
                        List<Ingredient> ingredients = new ArrayList<>();
                        for (DataSnapshot ingredientSnapshot : task.getResult().getChildren()) {
                            Ingredient ingredient = ingredientSnapshot.getValue(Ingredient.class);
                            if (ingredient != null) {
                                ingredients.add(ingredient);
                            }
                        }
                        System.out.println("Pantry loaded");
                        callback.onComplete(ingredients);
                    } else {
                        System.out.println("Pantry not loaded");
                        callback.onComplete(null);
                    }
                });
    }

    //Write to a global table: Cookbook that sends in a Recipe object
    public void writeToCookbookDB(Recipe recipe, RecipeCallback callback) {
        fbInstance.getReference("Cookbook").child(recipe.getRecipeName()).setValue(recipe)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Recipe added to cookbook database");
                        callback.onComplete(recipe);
                    } else {
                        System.out.println("Recipe not added to cookbook database");
                        callback.onComplete(null);
                    }
                });
    }

    //Read all the recipes from the Cookbook table
    public void readFromCookbookDB(RecipeListCallback callback) {
        List<Recipe> recipes = new ArrayList<>();
        fbInstance.getReference("Cookbook").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DataSnapshot recipeSnapshot : task.getResult().getChildren()) {
                            Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                            if (recipe != null) {
                                recipes.add(recipe);
                            }
                        }
                        System.out.println("Recipes found in cookbook database");
                        callback.onComplete(recipes);
                    } else {
                        System.out.println("Recipes not found in cookbook database");
                        callback.onComplete(null);
                    }
                });
    }

    //Queries Cookbook database through a recipe name
    public void getRecipeByName(String recipeName, RecipeCallback callback) {
        Recipe recipe = new Recipe();
        fbInstance.getReference("Cookbook").child(recipeName).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        recipe.setRecipe(task.getResult().getValue(Recipe.class));
                        System.out.println("Recipe found in cookbook database");
                        callback.onComplete(recipe);
                    } else {
                        System.out.println("Recipe not found in cookbook database");
                        callback.onComplete(null);
                    }
                });
    }


    public interface UserCallback {
        void onComplete(User user);
    }

    public interface MealCallback {
        void onComplete(Meal meal);
    }

    //Return a list of Ingredients
    public interface PantryCallback {
        void onComplete(List<Ingredient> ingredients);
    }

    public interface RecipeCallback {
        void onComplete(Recipe recipe);
    }

    public interface RecipeListCallback {
        void onComplete(List<Recipe> recipes);
    }
}
