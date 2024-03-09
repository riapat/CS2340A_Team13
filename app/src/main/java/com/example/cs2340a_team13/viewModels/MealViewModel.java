package com.example.cs2340a_team13.viewModels;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.User; // User class

import java.util.Date;

public class MealViewModel {
    private Meal meal;
    private User currentUser; // Reference to current user
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

        meal.setMeal(mealName);
        meal.setCalorieCount(calories);

        // new date object
        Date currentDate = new Date();
        meal.setDate(currentDate);

        // write to meal db
        databaseAccess.writeToMealsDB(meal);

        // Add created meal to array of user
        if (currentUser != null) {
            currentUser.addMeal(meal);
            // Update the user's entry in the database
            databaseAccess.updateToUserDB(currentUser);
        }
        return meal;
    }

}

