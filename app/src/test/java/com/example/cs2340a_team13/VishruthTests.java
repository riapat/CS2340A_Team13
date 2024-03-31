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
    private IngredientScreen ingredientScreen;

    @Before
    public void setUp() {
        ingredientScreen = new IngredientScreen();
    }

    @Test
    public void testInputIngredientsButtonClick() {
        // Initially, fields should be invisible
        assertEquals(View.GONE, ingredientScreen.findViewById(R.id.ingredientNameEditText).getVisibility());
        assertEquals(View.GONE, ingredientScreen.findViewById(R.id.quantityEditText).getVisibility());
        assertEquals(View.GONE, ingredientScreen.findViewById(R.id.caloriesEditText).getVisibility());
        assertEquals(View.GONE, ingredientScreen.findViewById(R.id.expirationDateEditText).getVisibility());

        // Clicking input ingredients button
        ingredientScreen.findViewById(R.id.InputIngredients).performClick();

        // After clicking, fields should be visible
        assertEquals(View.VISIBLE, ingredientScreen.findViewById(R.id.ingredientNameEditText).getVisibility());
        assertEquals(View.VISIBLE, ingredientScreen.findViewById(R.id.quantityEditText).getVisibility());
        assertEquals(View.VISIBLE, ingredientScreen.findViewById(R.id.caloriesEditText).getVisibility());
        assertEquals(View.VISIBLE, ingredientScreen.findViewById(R.id.expirationDateEditText).getVisibility());
    }

    @Test
    public void testSubmitButtonClick() {
        EditText ingredientNameEditText = ingredientScreen.findViewById(R.id.ingredientNameEditText);
        EditText quantityEditText = ingredientScreen.findViewById(R.id.quantityEditText);
        EditText caloriesEditText = ingredientScreen.findViewById(R.id.caloriesEditText);
        EditText expirationDateEditText = ingredientScreen.findViewById(R.id.expirationDateEditText);

        // Set some text in EditText fields
        ingredientNameEditText.setText("Test Ingredient");
        quantityEditText.setText("10");
        caloriesEditText.setText("50");
        expirationDateEditText.setText("2024-04-01");

        // Clicking submit button
        ingredientScreen.findViewById(R.id.submitButton).performClick();

        // After clicking, EditText fields should be empty
        assertEquals("", ingredientNameEditText.getText().toString());
        assertEquals("", quantityEditText.getText().toString());
        assertEquals("", caloriesEditText.getText().toString());
        assertEquals("", expirationDateEditText.getText().toString());
    }
}
