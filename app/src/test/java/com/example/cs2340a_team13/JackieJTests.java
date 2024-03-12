package com.example.cs2340a_team13;

import static org.junit.Assert.assertEquals;

import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.viewModels.MealViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;
import org.junit.Test;

public class JackieJTests {
    @Test
    public void testNullUser() {
        UserViewModel userN = new UserViewModel();
        double testNull = userN.calculateCalories(userN);
        assertEquals(testNull, 0.0);
    }
    @Test
    public void testFemaleUser() {
        UserViewModel userF = new UserViewModel();
        userF.getUser().setAge(20);
        userF.getUser().setGender("Female");
        userF.getUser().setHeight(162);
        userF.getUser().setWeight(70);
        double testF = userF.calculateCalories(userF);
        assertEquals(testF, 1741.8);
    }

    @Test
    public void testMaleUser() {
        UserViewModel userM = new UserViewModel();
        userM.getUser().setAge(22);
        userM.getUser().setGender("Male");
        userM.getUser().setHeight(175);
        userM.getUser().setWeight(85);
        double testM = userM.calculateCalories(userM);
        assertEquals(testM, 2206.5);
    }

}