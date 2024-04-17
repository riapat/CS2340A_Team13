package com.example.cs2340a_team13;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.viewModels.ShoppingListViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RiaPatelTests {

    private ShoppingListViewModel shoppingListViewModel;
    @Before
    public void setUp() {
        User dummyUser = new User("Dummy User", "dummy@example.com");
        UserViewModel.getInstance().setTestUser(dummyUser);
        shoppingListViewModel = ShoppingListViewModel.getInstance();
    }
// Testing User logged Meals cleared
    @Test
    public void testClearLoggedMeals() {
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
    public void testEmptyDateLoggedMeal() {
        User user = new User("test", "testing");
        user.addMeal(new Meal("Pizza", 300));
        //Initialized Date
        Date expectedDate = new Date();
        assertEquals(expectedDate,user.getMeals().get(0).getDate());
    }

    // Check if ingredient does not exist in pantry
    @Test
    public void testCheckIfIngredientExistsInPantry_WhenIngredientDoesNotExist_ShouldReturnNull() {
        User user = UserViewModel.getInstance().getUser();
        List<Ingredient> pantryIngredients = new ArrayList<>();
        pantryIngredients.add(new Ingredient("Existing Ingredient 1", 1, 100, "2024-04-15"));
        pantryIngredients.add(new Ingredient("Existing Ingredient 2", 1, 100, "2024-04-30"));
        user.setPantryIngredients(pantryIngredients);


        Ingredient result = shoppingListViewModel.checkIfIngredientExistsInPantry("Non-Existing Ingredient");

        assertNull("Ingredient should not be found in the pantry", result);
    }

    //Does not exist in Shopping List
    @Test
    public void testGetExistingIngredient_WhenIngredientDoesNotExist_ShouldReturnNull() {
        User user = UserViewModel.getInstance().getUser();
        List<Ingredient> shoppingList = new ArrayList<>();
        shoppingList.add(new Ingredient("Flour", 2, 0, ""));
        shoppingList.add(new Ingredient("Sugar", 5, 0, ""));
        user.setShoppingList(shoppingList);

        Ingredient result = shoppingListViewModel.getExistingIngredient("Salt");

        assertNull(result);
    }
}
