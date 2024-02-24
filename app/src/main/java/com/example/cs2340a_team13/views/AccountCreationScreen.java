package com.example.cs2340a_team13.views;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.viewModels.LoginViewModel;

public class AccountCreationScreen extends AppCompatActivity {
    private EditText createUsernameEditText;
    private EditText createPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button createAccountScreenButton;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation_screen);

        createUsernameEditText = findViewById(R.id.usernameFieldAACS);
        createPasswordEditText = findViewById(R.id.passwordFieldAACS);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordFieldAACS);
        createAccountScreenButton = findViewById(R.id.createAccountButtonAACS);

        loginViewModel = LoginViewModel.getInstance();
    }
    public void createPasswordScreenButtonClicked(View v) {
        //Verify no null fields, remove whitespace from all fields, and that password and confirm password match
        boolean emptyCheck = true;
        String createUsername = "";
        String createPassword = "";
        String confirmPassword = "";

        /* Binds fields to variables, checking for null and whitespace */
        if (createUsernameEditText != null) {
            createUsername = createUsernameEditText.getText().toString().trim();
            if (createUsername.isEmpty()) {
                emptyCheck = false;
                createUsernameEditText.setError(("Please enter a Username."));
                createUsernameEditText.requestFocus();
            }
        } else {
            Toast.makeText(AccountCreationScreen.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
        }

        if (createPasswordEditText != null) {
            createPassword = createPasswordEditText.getText().toString().trim();
            if (createPassword.isEmpty() || createPassword.length() < 6) {
                emptyCheck = false;
                createPasswordEditText.setError("Please enter a password that is greater than 6 characters.");
                createPasswordEditText.requestFocus();
            }
        } else {
            Toast.makeText(AccountCreationScreen.this, "Please enter a password that is greater than 6 characters.ria", Toast.LENGTH_SHORT).show();
        }

        if (confirmPasswordEditText != null) {
            confirmPassword = confirmPasswordEditText.getText().toString().trim();
            if (confirmPassword.isEmpty()) {
                emptyCheck = false;
                confirmPasswordEditText.setError("Please verify your password.");
                confirmPasswordEditText.requestFocus();
            }
        } else {
            Toast.makeText(AccountCreationScreen.this, "Please verify your password.", Toast.LENGTH_SHORT).show();
        }

        //Checks if any fields are empty before creating account
        if (emptyCheck) {
            //Checks that Password and Confirmed Password match
            if (createPassword.equals(confirmPassword)) {
                //Create Account Logic Goes Here
                if (!(loginViewModel.signUp(createUsername, createPassword))) {
                    //If account is created, return to login screen
                    Toast
                            .makeText(AccountCreationScreen.this,
                                    "Invalid username or password",
                                    Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AccountCreationScreen.this, "Account Created", Toast.LENGTH_LONG).show();
                    createUsernameEditText.setText("");
                    createPasswordEditText.setText("");
                    confirmPasswordEditText.setText("");

                    //code navigation here!
                }
            } else {
                confirmPasswordEditText.setError("Passwords do not match");
                confirmPasswordEditText.requestFocus();
            }
        }
    }
}