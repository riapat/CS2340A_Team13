package com.example.cs2340a_team13;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.viewModels.ShoppingListViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class VishruthTestSprint4 {

    private ShoppingListViewModel shoppingListViewModel;

    @Before
    public void setUp() {
        shoppingListViewModel = ShoppingListViewModel.getInstance();
    }

    @Test
    public void testCheckIfIngredientExistsInPantry_WhenIngredientExists_ShouldReturnIngredient() {
        User user = UserViewModel.getInstance().getUser();
        List<Ingredient> pantryIngredients = new ArrayList<>();
        pantryIngredients.add(new Ingredient("Existing Ingredient 1", 1, 100, "2024-04-01"));
        user.setPantryIngredients(pantryIngredients);

        Ingredient result = shoppingListViewModel.checkIfIngredientExistsInPantry("Existing Ingredient 1");

        assert result != null;
        assertEquals("Ingredient should exist in pantry", "Existing Ingredient 1", result.getIngredientName());

    }
    @Test
    public void testGetExistingIngredient_WhenIngredientExists_ShouldReturnIngredient() {
        User user = UserViewModel.getInstance().getUser();
        List<Ingredient> shoppingList = new ArrayList<>();
        shoppingList.add(new Ingredient("Flour", 2, 0, ""));
        shoppingList.add(new Ingredient("Sugar", 5, 0, ""));
        user.setShoppingList(shoppingList);

        Ingredient result = shoppingListViewModel.getExistingIngredient("Sugar");

        assert result != null;
        assertEquals("Sugar", result.getIngredientName());
        assertEquals(5, result.getQuantity());
    }
}
