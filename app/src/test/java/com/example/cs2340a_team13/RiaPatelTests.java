package com.example.cs2340a_team13;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.User;
import java.util.Date;

public class RiaPatelTests {

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
}
