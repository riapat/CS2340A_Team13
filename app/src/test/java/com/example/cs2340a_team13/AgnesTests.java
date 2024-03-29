package com.example.cs2340a_team13;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.viewModels.UserViewModel;

import org.junit.Test;

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
}
