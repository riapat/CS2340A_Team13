package com.example.cs2340a_team13.viewModels;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.User; // User class

import java.util.Date;

public class MealViewModel {

    private UserViewModel userViewModel = UserViewModel.getInstance();
    private Meal meal = null;
    private User currentUser = userViewModel.getUser(); // Reference to current user
    private static MealViewModel instance;

    public static synchronized MealViewModel getInstance(){
        if (instance == null){
            instance = new MealViewModel();
        }
        return instance;
    }
    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    public Meal createMeal(String mealName, int calories) {
        if (meal == null) {
            meal = new Meal();
        }

        meal.setName(mealName);
        meal.setCalorieCount(calories);

        // new date object
        Date currentDate = new Date();
        meal.setDate(currentDate);

        // write to meal db
        databaseAccess.writeToMealsDB(meal, mealCallback -> {
            if (mealCallback != null) {
                System.out.println("Meal added to meal database");
                meal = mealCallback;
            } else {
                System.out.println("Meal not added to meal database");
            }
        });

        // Add created meal to array of user
        if (currentUser != null) {
            currentUser.addMeal(meal);
            // Update the user's entry in the database
            databaseAccess.updateToUserDB(currentUser, userCallback -> {
                if (userCallback != null) {
                    System.out.println("User updated in user database");
                } else {
                    System.out.println("User not updated in user database");
                }
            });
        }
        return meal;
    }

}

