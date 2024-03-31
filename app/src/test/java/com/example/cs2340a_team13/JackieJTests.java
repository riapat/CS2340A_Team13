package com.example.cs2340a_team13;

import static org.junit.Assert.assertEquals;

import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.Recipe;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.viewModels.MealViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JackieJTests {
    @Test
    public void testNullUser() {
        User nullUser = null;
        UserViewModel.getInstance().setTestUser(nullUser);
        double testNull = UserViewModel.getInstance().calculateCalories();
        assertEquals(testNull, 0.0);
    }
    @Test
    public void testFemaleUser() {
        User userF =  new User();
        userF.setAge(20);
        userF.setGender("Female");
        userF.setHeight(162);
        userF.setWeight(70);
        UserViewModel.getInstance().setTestUser(userF);
        double testF = UserViewModel.getInstance().calculateCalories();
        assertEquals(testF, 1741.8);
    }
    @Test
    public void testMaleUser() {
        User userM = new User();
        userM.setAge(22);
        userM.setGender("Male");
        userM.setHeight(175);
        userM.setWeight(85);
        UserViewModel.getInstance().setTestUser(userM);
         double testM = UserViewModel.getInstance().calculateCalories();
        assertEquals(testM, 2206.5);
    }

    @Test
    public void testAddIngredientsNullCheck(){
        Ingredient oreo = new Ingredient("Oreo", 5, 150);
        List<Ingredient> items = new ArrayList<Ingredient>();
        items.add(oreo);
        Recipe milkshake = new Recipe("MilkShake", null, items, null );
        milkshake.addIngredient(oreo);
        Assert.assertEquals(milkshake.getRecipeIngredients(),items);
    }
    @Test
    public void testAddIngredients(){
        Ingredient oreo = new Ingredient("Oreo", 5, 150);
        Ingredient milk = new Ingredient("Milk", 10, 20);
        Ingredient iceCream = new Ingredient("IceCream", 5, 150);
        List<Ingredient> items = new ArrayList<Ingredient>();
        items.add(oreo);
        items.add(milk);
        Recipe milkshake = new Recipe("MilkShake", null, items, null );
        milkshake.addIngredient(oreo);
        Assert.assertNotEquals(milkshake.getRecipeIngredients(),items);
    }
    @Test
    public void testCalculateCalories(){
        Ingredient oreo = new Ingredient("Oreo", 5, 150);
        Ingredient milk = new Ingredient("Milk", 10, 20);
        Ingredient iceCream = new Ingredient("IceCream", 5, 150);
        List<Ingredient> items = new ArrayList<Ingredient>();
        items.add(oreo);
        items.add(milk);
        items.add(iceCream);
        Recipe milkshake = new Recipe("MilkShake", null, items, null );
        Assert.assertEquals(milkshake.calculateCaloriesPerServing(), 1705);
    }

    @Test
    public void testCalculateCaloriesNullRecipe(){
        List<Ingredient> items = null;
        Recipe milkshake = new Recipe("MilkShake", null, items, null );
        Assert.assertEquals(milkshake.calculateCaloriesPerServing(), 0);
    }

    @Test
    public void testCalculateCaloriesEmptyCheck(){
        List<Ingredient> items = new ArrayList<Ingredient>();
        Recipe water = new Recipe("items", null, items, null );
        Assert.assertEquals(water.calculateCaloriesPerServing(), 0);
    }



}