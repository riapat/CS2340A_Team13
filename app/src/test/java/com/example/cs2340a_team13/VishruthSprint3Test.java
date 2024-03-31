package com.example.cs2340a_team13;

import com.example.cs2340a_team13.model.Ingredient;

import com.example.cs2340a_team13.viewModels.IngredientViewModel;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VishruthSprint3Test {

    @Test
    public void testCheckDuplicate_WhenPantryIsEmpty_ShouldReturnFalse() {

        IngredientViewModel viewModel = IngredientViewModel.getInstance();
        Ingredient ingredient = new Ingredient("Test Ingredient", 1, 100, "2024-04-01");


        boolean result = viewModel.checkDuplicate(ingredient);

        assertFalse(result);
    }

    @Test
    public void testCheckDuplicate_WhenIngredientNotInPantry_ShouldReturnFalse() {
        IngredientViewModel viewModel = IngredientViewModel.getInstance();
        Ingredient ingredient = new Ingredient("Test Ingredient", 1, 100, "2024-04-01");
        viewModel.createIngredient("Another Ingredient", 2, 200, "2024-04-01");

        boolean result = viewModel.checkDuplicate(ingredient);


        assertFalse(result);
    }
}