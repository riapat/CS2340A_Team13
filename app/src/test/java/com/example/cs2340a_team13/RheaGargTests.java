package com.example.cs2340a_team13;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.viewModels.MealViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;
import org.junit.Test;

import java.util.Date;
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
        assertEquals(testS, 1452.0);
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
        assertEquals(testD, 1508.0);
    }
}
