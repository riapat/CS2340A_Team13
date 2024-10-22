package com.example.cs2340a_team13;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.Recipe;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.viewModels.IngredientViewModel;
import com.example.cs2340a_team13.viewModels.MealViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RheaGargTests {
    @Test
    public void testFemaleUser() {
        User woman =  new User();
        woman.setAge(30);
        woman.setGender("woman");
        woman.setHeight(120);
        woman.setWeight(60);
        UserViewModel.getInstance().setTestUser(woman);
        double testS = UserViewModel.getInstance().calculateCalories();
        assertEquals(1446.0, testS, 0.1);
    }
    @Test
    public void testMaleUser() {
        User man = new User();
        man.setAge(45);
        man.setGender("man");
        man.setHeight(160);
        man.setWeight(90);
        UserViewModel.getInstance().setTestUser(man);
        double testD = UserViewModel.getInstance().calculateCalories();
        assertEquals(1694.0, testD, 0.1);
    }

    IngredientViewModel ingredientViewModel = IngredientViewModel.getInstance();
    UserViewModel userViewModel = UserViewModel.getInstance();
    @Test
    public void testDuplicate() {

        User testUser = new User("Test User", "testUser1");

        IngredientViewModel.getInstance().setCurrentUser(testUser);
        UserViewModel.getInstance().setTestUser(testUser);
        List<Ingredient> pantry = new ArrayList<>();

        Ingredient ingredient1 = new Ingredient("Ingredient1", 10, 100);
        Ingredient ingredient2 = new Ingredient("Ingredient2", 20, 200);
        pantry.add(ingredient1);
        pantry.add(ingredient2);
        testUser.setPantryIngredients(pantry);

        Ingredient duplicateIngredient = new Ingredient("Ingredient1", 10, 100);
        assertTrue(ingredientViewModel.checkDuplicate(duplicateIngredient));
    }

    @Test
    public void testNonDuplicate() {
        User testUser = new User();
        List<Ingredient> pantry = new ArrayList<>();

        Ingredient ingredient1 = new Ingredient("Ingredient1", 10, 100);
        Ingredient ingredient2 = new Ingredient("Ingredient2", 20, 200);
        pantry.add(ingredient1);
        pantry.add(ingredient2);
        testUser.setPantryIngredients(pantry);
        userViewModel.setTestUser(testUser);

        Ingredient nonDuplicateIngredient = new Ingredient("Ingredient3", 15, 150);
        assertFalse(ingredientViewModel.checkDuplicate(nonDuplicateIngredient));
    }

    @Test
    public void testCalculateCaloriesIsNull(){
        List<Ingredient> items = null;
        Recipe pie = new Recipe("Pie", null, items, null );
        Assert.assertEquals(pie.calculateCaloriesPerServing(), 0);
    }

    @Test
    public void testCalculateCaloriesIsEmpty(){
        List<Ingredient> recipe = new ArrayList<Ingredient>();
        Recipe acai = new Recipe("Acai", null, recipe, null );
        Assert.assertEquals(acai.calculateCaloriesPerServing(), 0);
    }
}
