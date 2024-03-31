package com.example.cs2340a_team13;

import static org.junit.Assert.*;

import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.viewModels.IngredientViewModel;

import org.junit.Before;
import org.junit.Test;

public class VishruthSprint3Test {

    private IngredientViewModel ingredientViewModel;

    @Before
    public void setUp() {
        ingredientViewModel = IngredientViewModel.getInstance();
    }

    @Test
    public void testCreatingValidIngredient() {
        // Prepare test data
        String ingredientName = "TestIngredient";
        int quantity = 10;
        int calories = 100;
        String expirationDate = "2024-04-01";

        // Call method under test
        Ingredient createdIngredient = ingredientViewModel.createIngredient(ingredientName, quantity, calories, expirationDate);

        // Verify the ingredient is created with correct attributes
        assertNotNull(createdIngredient);
        assertEquals(ingredientName, createdIngredient.getIngredientName());
        assertEquals(quantity, createdIngredient.getQuantity());
        assertEquals(calories, createdIngredient.getCalories());
        assertEquals(expirationDate, createdIngredient.getExpirationDate());
    }
    @Test
    public void testSubmitButtonWithEmptyName() {
        // Prepare test data
        String ingredientName = "";
        int quantity = 10; // Example value
        int calories = 100; // Example value
        String expirationDate = ""; // Example value

        // Call method under test
        Ingredient createdIngredient = ingredientViewModel.createIngredient(ingredientName, quantity, calories, expirationDate);

        // Verify the ingredient is not created
        assertNull(createdIngredient);
    }

}