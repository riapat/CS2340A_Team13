package com.example.cs2340a_team13.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        loginViewModel = LoginViewModel.getInstance();

        editTextUsername = findViewById(R.id.usernameEditText);
        editTextPassword = findViewById(R.id.passwordEditText);

        createAccountButton = findViewById(R.id.createAccountButton);
        loginButton = findViewById(R.id.logInButton);

        mAuth = FirebaseAuth.getInstance();
    }

    public void createAccountButtonClicked(View v) {
        // This will take the user to the create account screen

    }

    public void logInButtonClicked(View v) {

        String username = "";
        String password = "";

        //Removes the whitespace from the username and password and null checks them
        if (editTextUsername != null) {
            username = editTextUsername.getText().toString().trim();
        }
        if (editTextPassword != null) {
            password = editTextPassword.getText().toString().trim();
        }

        // Checks if the username and password are empty, if so, it will display an error message.
        if (username.isEmpty() && editTextUsername != null) {
            editTextUsername.setError(("Please enter a username."));
            editTextUsername.requestFocus(); // Puts the cursor in the username field
        }
        if (password.isEmpty() && editTextPassword != null) {
            editTextPassword.setError("Please enter a password.");
            editTextPassword.requestFocus(); // Puts the cursor in the password field
        }

        // If the username and password are not empty, it will attempt to log in the user.
        if (!username.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginScreen.this, "Successful Sign-In", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginScreen.this, "Sign-In Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                );
        }

    }



}