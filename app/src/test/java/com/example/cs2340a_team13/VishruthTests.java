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

        Meal meal = mealViewModel.createMeal(mealName, calories);
        assertEquals(mealName, meal.getMealName());
        assertEquals(calories, meal.getCalorieCount());
    }

    @Test
    public void testCreateMealWithNegativeCalories() {
        MealViewModel mealViewModel = MealViewModel.getInstance();
        String mealName = "Pizza";
        int calories = -200;

        Meal meal = mealViewModel.createMeal(mealName, calories);
        // Since calories are negative, the meal object should not be created
        assertEquals(null, meal);
    }
}

