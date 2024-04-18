package com.example.cs2340a_team13;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import android.view.View;
import android.widget.EditText;

import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.viewModels.IngredientViewModel;
import com.example.cs2340a_team13.viewModels.MealViewModel;
import com.example.cs2340a_team13.views.IngredientScreen;

public class VishruthTests {

    @Test
    public void testCreateMealWithValidInput() {
        MealViewModel mealViewModel = MealViewModel.getInstance();
        String mealName = "Salad";
        int calories = 350;

        mealViewModel.createMeal(mealName, calories, meal -> {
            assertEquals(mealName, meal.getMealName());
            assertEquals(calories, meal.getCalorieCount());

        });
    }

    @Test
    public void testCreateMealWithNegativeCalories() {
        MealViewModel mealViewModel = MealViewModel.getInstance();
        String mealName = "Pizza";
        int calories = -200;

        mealViewModel.createMeal(mealName, calories, meal -> {
            assertEquals(null, meal);
        });
        // Since calories are negative, the meal object should not be created
    }
}

