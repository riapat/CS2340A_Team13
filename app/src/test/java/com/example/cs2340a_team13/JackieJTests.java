package com.example.cs2340a_team13;

import static org.junit.Assert.assertEquals;

import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.viewModels.MealViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;
import org.junit.Test;

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

}