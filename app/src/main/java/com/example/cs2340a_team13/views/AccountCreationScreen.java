package com.example.cs2340a_team13.views;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cs2340a_team13.R;

public class AccountCreationScreen extends AppCompatActivity {
    private EditText createUsernameEditText;
    private EditText createPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button createAccountScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation_screen);

        createUsernameEditText = findViewById(R.id.usernameFieldAACS);
        createPasswordEditText = findViewById(R.id.passwordFieldAACS);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordFieldAACS);
        createAccountScreenButton = findViewById(R.id.createAccountButtonAACS);
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

        }

        if (createPasswordEditText != null) {
            createPassword = createPasswordEditText.getText().toString().trim();
            if (createPassword.isEmpty()) {
                emptyCheck = false;
                createPasswordEditText.setError("Please enter a password.");
                createPasswordEditText.requestFocus();
            }
        }

        if (confirmPasswordEditText != null) {
            confirmPassword = confirmPasswordEditText.getText().toString().trim();
            if (confirmPassword.isEmpty()) {
                emptyCheck = false;
                confirmPasswordEditText.setError("Please verify your password.");
                confirmPasswordEditText.requestFocus();
            }
        }

        //Checks if any fields are empty before creating account
        if (emptyCheck) {
            //Checks that Passsword and Confirmed Password match
            if(createPassword.equals(confirmPassword)){
                //Create Account Logic Goes Here
                
            }else{
                confirmPasswordEditText.setError("Passwords do not match");
                confirmPasswordEditText.requestFocus();
            }
        }
    }
}