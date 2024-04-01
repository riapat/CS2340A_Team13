package com.example.cs2340a_team13;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.Recipe;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.viewModels.UserViewModel;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AgnesTests {
    @Test
    public void testWUser() {
        User userF =  new User();
        userF.setAge(27);
        userF.setGender("W");
        userF.setHeight(130);
        userF.setWeight(178);
        UserViewModel.getInstance().setTestUser(userF);
        double testF = UserViewModel.getInstance().calculateCalories();
        assertEquals(testF, 2955.0, 0.1);
    }
    @Test
    public void testMUser() {
        User userM = new User();
        userM.setAge(22);
        userM.setGender("M");
        userM.setHeight(175);
        userM.setWeight(85);
        UserViewModel.getInstance().setTestUser(userM);
        double testM = UserViewModel.getInstance().calculateCalories();
        assertEquals(testM, 1842.75, 0.2);
    }

    @Test
    public void ClearLoggedMeals() {
        User user = new User("test", "testing");
        user.addMeal(new Meal("Pizza", 300));
        user.addMeal(new Meal("Burger", 500));
        user.clearLoggedMeals();
        user.addMeal(new Meal("", 0));
        assertEquals(1, user.getMeals().size());
        user.getMeals().get(0).setName("Rice");
        assertEquals("Rice", user.getMeals().get(0).getMealName());

        User user2 = new User("test2", "testing2");
        user2.addMeal(new Meal("Coca Cola", 150));
        assertNotEquals("Rice", user2.getMeals().get(0).getMealName());
    }
    @Test
    public void testIsIngredientsEnough_IngredientsSufficient() {
        // Setup pantry with ingredients
        List<Ingredient> pantry = Arrays.asList(
                new Ingredient("Flour", 500, 400, "may3"),
                new Ingredient("Sugar", 200, 300, "may4"),
                new Ingredient("Eggs", 3, 5, "may5")
        );

        // Recipe needs
        List<Ingredient> recipeIngredients = Arrays.asList(
                new Ingredient("Sugar", 100, 200, "may6"),
                new Ingredient("Flour", 300, 400, "may7"),
                new Ingredient("Eggs", 2, 5, "may8")
        );

        // Test
        Recipe recipe = new Recipe("Cake", "A delicious cake", recipeIngredients, "Instructions");
        assertTrue("All required ingredients are present in sufficient quantities",
                recipe.isIngredientsEnough(pantry, recipeIngredients));
    }

    @Test
    public void testIsIngredientsEnough_IngredientsSufficient2() {
        // Setup pantry with ingredients
        List<Ingredient> pantry = Arrays.asList(
                new Ingredient("Sugar", 100, 200, "may6"),
                new Ingredient("Flour", 300, 400, "may7"),
                new Ingredient("Eggs", 2, 5, "may8")
        );

        // Recipe needs
        List<Ingredient> recipeIngredients = Arrays.asList(
                new Ingredient("Flour", 500, 400, "may3"),
                new Ingredient("Sugar", 200, 200, "may4"),
                new Ingredient("Eggs", 3, 5, "may5")
        );

        // Test
        Recipe recipe = new Recipe("Cake", "A delicious cake", recipeIngredients, "Instructions");
        assertFalse("All required ingredients are present in sufficient quantities",
                recipe.isIngredientsEnough(pantry, recipeIngredients));
    }
}
