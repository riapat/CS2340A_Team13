package com.example.cs2340a_team13;

import static org.junit.Assert.assertEquals;

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
}
