package com.example.cs2340a_team13.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.viewModels.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button createAccountButton;
    private Button loginButton;
    private Button exitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        loginViewModel = LoginViewModel.getInstance();

        editTextUsername = findViewById(R.id.usernameEditText);
        editTextPassword = findViewById(R.id.passwordEditText);

        createAccountButton = findViewById(R.id.createAccountButton);
        loginButton = findViewById(R.id.logInButton);
        exitButton = findViewById(R.id.ExitButton);

    }

    public void createAccountButtonClicked(View v) {
        // This will take the user to the AccountCreationScreen when the create account button is clicked
        Intent intent = new Intent(LoginScreen.this, AccountCreationScreen.class);
        editTextUsername.setText("");
        editTextPassword.setText("");
        startActivity(intent);
    }

    public void logInButtonClicked(View v) {
        if (editTextUsername.getText() == null) {
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
        } else if (editTextPassword.getText() == null) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
        }
        String usernameText = editTextUsername.getText().toString().trim();
        String passwordText = editTextPassword.getText().toString().trim();
        boolean response = loginViewModel.signIn(usernameText, passwordText);
        if (!response) {
            Toast.makeText(LoginScreen.this, "Invalid username or password", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginScreen.this, "Login successful", Toast.LENGTH_LONG).show();
            editTextUsername.setText("");
            editTextPassword.setText("");
            Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
        }

    }

    //exit the app if exit button is clicked
    public void createExitButtonClicked(View v) {
        editTextUsername.setText("");
        editTextPassword.setText("");
        finish();
        System.exit(0);
    }
}