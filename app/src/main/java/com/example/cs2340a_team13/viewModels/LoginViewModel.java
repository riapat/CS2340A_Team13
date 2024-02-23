package com.example.cs2340a_team13.viewModels;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cs2340a_team13.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel {
    private User user;
    private static LoginViewModel instance;

    private FirebaseAuth mAuth;

    public LoginViewModel() {
        user = new User();

    }

    public static synchronized LoginViewModel getInstance() {
        if (instance == null) {
            instance = new LoginViewModel();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateUser(String username, String password) {
        this.user.setUsername(username);
        this.user.setPassword(password);
    }

    public void createUser(String username, String password) {
        user = new User(username, password);
    }

    public boolean signIn(String username, String password) {
        mAuth = FirebaseAuth.getInstance();
        // Checks if the username and password are not null and not empty.
        if (username != null && password != null) {
            if (username.isEmpty() || password.isEmpty()) {
                return false;
            }
        } else {
            return false;
        }


        // Attempts to sign in with the given username and password.
        // If the username and password are valid, the user is signed in.
        // If the username and password are invalid, the user is not signed in.
        mAuth.signInWithEmailAndPassword(username + "@mail.com", password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LoginViewModel", "signInWithEmail:success");
                        } else {
                            Log.w("LoginViewModel", "signInWithEmail:failure", task.getException());
                        }
                    }
                });

        return mAuth.getCurrentUser() != null;

    }
}
