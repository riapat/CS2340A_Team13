package com.example.cs2340a_team13;

import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.viewModels.IngredientViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VishruthSprint3Test {

    private IngredientViewModel viewModel;

//    @Before
//    public void setUp() {
//        // Create a dummy user for testing
//        User dummyUser = new User("Dummy User", "dummy@example.com");
//
//        // Instantiate IngredientViewModel
//        viewModel = IngredientViewModel.getInstance();
//
//        // Set the dummy user in the currentUser field directly
//        viewModel.setCurrentUser(dummyUser);
//
//
//        List<Ingredient> pantryIngredients = new ArrayList<>();
//        pantryIngredients.add(new Ingredient("Existing Ingredient 1", 1, 100, "2024-04-01"));
//        pantryIngredients.add(new Ingredient("Existing Ingredient 2", 1, 100, "2024-04-01"));
//        dummyUser.setPantryIngredients(pantryIngredients);
//    }

    @Test
    public void testCheckDuplicate_WhenIngredientExistsInPantry_ShouldReturnTrue() {

        User dummyUser = new User("Dummy User", "dummy@example.com");

        // Instantiate IngredientViewModel
        viewModel = IngredientViewModel.getInstance();

        UserViewModel.getInstance().setTestUser(dummyUser);
        viewModel.setCurrentUser(dummyUser);

        List<Ingredient> pantryIngredients = new ArrayList<>();
        pantryIngredients.add(new Ingredient("Existing Ingredient 1", 1, 100, "2024-04-01"));
        pantryIngredients.add(new Ingredient("Existing Ingredient 2", 1, 100, "2024-04-01"));
        dummyUser.setPantryIngredients(pantryIngredients);

        assertEquals("Pantry ingredients should be set", 2, UserViewModel.getInstance().getUser().getPantryIngredients().size());
        Ingredient existingIngredient = new Ingredient("Existing Ingredient 1", 1, 100, "2024-04-01");

        boolean result = viewModel.checkDuplicate(existingIngredient);
        System.out.println(result);

        assertTrue("When ingredient exists in pantry, checkDuplicate should return true", result);
    }

    @Test
    public void testCheckDuplicate_WhenIngredientDoesNotExistInPantry_ShouldReturnFalse() {

        User dummyUser = new User("Dummy User", "dummy@example.com");

        // Instantiate IngredientViewModel
        viewModel = IngredientViewModel.getInstance();

        UserViewModel.getInstance().setTestUser(dummyUser);
        viewModel.setCurrentUser(dummyUser);

        List<Ingredient> pantryIngredients = new ArrayList<>();
        pantryIngredients.add(new Ingredient("Existing Ingredient 1", 1, 100, "2024-04-01"));
        pantryIngredients.add(new Ingredient("Existing Ingredient 2", 1, 100, "2024-04-01"));
        dummyUser.setPantryIngredients(pantryIngredients);

        Ingredient nonExistingIngredient = new Ingredient("Non-existing Ingredient", 1, 100, "2024-04-01");

        boolean result = viewModel.checkDuplicate(nonExistingIngredient);

        assertFalse("When ingredient does not exist in pantry, checkDuplicate should return false", result);
    }
}
