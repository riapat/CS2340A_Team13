package com.example.cs2340a_team13;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.cs2340a_team13.viewModels.LoginViewModel;

public class LoginManagerTest {

    LoginViewModel viewModel = LoginViewModel.getInstance();

    // Test for preventing login with null username
    @Test
    public void testNullUsernameLogin() {
        viewModel.signIn(null, "password", (isSuccess, user) -> {
            assertFalse(isSuccess);
        });
    }

    // Test for preventing login with null password
    @Test
    public void testNullPasswordLogin() {
        viewModel.signIn("username", null, (isSuccess, user) -> {
            assertFalse(isSuccess);
        });
    }

    // Test for preventing login with empty username
    @Test
    public void testEmptyUsernameLogin() {
        viewModel.signIn("", "password", (isSuccess, user) -> {
            assertFalse(isSuccess);
        });
    }

    // Test for preventing login with empty password
    @Test
    public void testEmptyPasswordLogin() {
        viewModel.signIn("username", "", (isSuccess, user) -> {
            assertFalse(isSuccess);
        });
    }

    // Test for preventing login with invalid username and password combination
    @Test
    public void testInvalidUsernamePasswordLogin() {
        viewModel.signIn("invalid_username", "invalid_password",
                (isSuccess, user) -> {
            assertFalse(isSuccess);
        });
    }

    // Test for allowing login with valid username and password
    @Test
    public void testValidLogin() {
        viewModel.signIn("valid_username", "valid_password",
                (isSuccess, user) -> {
            assertTrue(isSuccess);
        });
    }
    
    // Test for preventing login with only spaces in username and or password
    @Test
    public void testSpacesInUsernamePasswordLogin() {
        viewModel.signIn(" ", "password", (isSuccess, user) -> {
            assertFalse(isSuccess);
        });
        viewModel.signIn("username", " ", (isSuccess, user) -> {
            assertFalse(isSuccess);
        });
        viewModel.signIn(" ", " ", (isSuccess, user) -> {
            assertFalse(isSuccess);
        });
    }
}
