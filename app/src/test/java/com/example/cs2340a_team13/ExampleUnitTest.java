package com.example.cs2340a_team13;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.cs2340a_team13.viewModels.LoginViewModel;

public class ExampleUnitTest {

    // Test for preventing login with null username
    @Test
    public void testNullUsernameLogin() {
        LoginViewModel viewModel = LoginViewModel.getInstance();
        assertFalse(viewModel.signUp(null, "password"));
    }

    // Test for preventing login with null password
    @Test
    public void testNullPasswordLogin() {
        LoginViewModel viewModel = LoginViewModel.getInstance();
        assertFalse(viewModel.signUp("username", null));
    }

    // Test for preventing login with empty username
    @Test
    public void testEmptyUsernameLogin() {
        LoginViewModel viewModel = LoginViewModel.getInstance();
        assertFalse(viewModel.signUp("", "password"));
    }

    // Test for preventing login with empty password
    @Test
    public void testEmptyPasswordLogin() {
        LoginViewModel viewModel = LoginViewModel.getInstance();
        assertFalse(viewModel.signUp("username", ""));
    }

    // Test for allowing login with valid username and password
    @Test
    public void testValidLogin() {
        LoginViewModel viewModel = LoginViewModel.getInstance();
        assertTrue(viewModel.signUp("valid_username", "valid_password"));
    }

    //Test that password is greater than 6 characters
    @Test
    public void testPasswordLength() {
        LoginViewModel viewModel = LoginViewModel.getInstance();
        assertFalse(viewModel.signUp("valid_username", "pass"));
        assertTrue(viewModel.signUp("valid_username", "password"));
    }

    //Test that both inputs are not null
    @Test
    public void testNullInputs() {
        LoginViewModel viewModel = LoginViewModel.getInstance();
        assertFalse(viewModel.signUp(null, null));
    }

    //Test that both inputs are not just whitespace
    @Test
    public void testWhitespaceInputs() {
        LoginViewModel viewModel = LoginViewModel.getInstance();
        assertFalse(viewModel.signUp(" ", " "));
        assertFalse(viewModel.signUp("", "password"));
        assertFalse(viewModel.signUp("username", ""));
        assertFalse(viewModel.signUp(". ", " ."));
    }


}