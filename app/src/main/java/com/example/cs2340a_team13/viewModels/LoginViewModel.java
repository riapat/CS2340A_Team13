package com.example.cs2340a_team13.viewModels;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
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

    public User createUser(String username, String password) {
        user = new User(username, password);
        return user;
    }

    public void signIn(String username, String password, AuthResultCallback callback) {
        Log.d("LoginViewModel", "signInAttempt: " + username + " " + password);
        mAuth = FirebaseAuth.getInstance();
        // Checks if the username and password are not null and not empty.
        if (username != null && password != null) {
            if (username.isEmpty() || password.isEmpty()) {
                callback.onComplete(false, null);
                return;
            }
        } else {
            callback.onComplete(false, null);
            return;
        }


        // Attempts to sign in with the given username and password.
        // If the username and password are valid, the user is signed in.
        // If the username and password are invalid, the user is not signed in.
        mAuth.signInWithEmailAndPassword(username + "@mail.com", password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseAccess.getInstance()
                                    .readFromUserDB(username, user -> {
                                        if (user != null) {

                                            DatabaseAccess.getInstance().loadPantry(username,
                                                    ingredients -> {
                                                    if (ingredients != null) {
                                                        // If pantry loaded successfully
                                                        // update user's pantry and complete sign-in
                                                        user.setPantryIngredients(ingredients);
                                                        // Ensure User model
                                                        //has a method to set pantry
                                                        updateUser(user);
                                                        Log.d("LoginViewModel",
                                                                "signInWithEmail:success Username: "
                                                                        + user.getUsername()
                                                                        + " Password: "
                                                                        + user.getPassword());
                                                        callback.onComplete(true, user);
                                                    } else {
                                                        // Handle failure to load pantry here
                                                        // if necessary
                                                        Log.w("LoginViewModel",
                                                                "Failed to load pantry");
                                                        callback.onComplete(false,
                                                                null);
                                                    }
                                                });
                                        } else {
                                            Log.w("LoginViewModel",
                                                    "signInWithEmail:failure ",
                                                    task.getException());
                                            callback.onComplete(false, null);
                                        }
                                    });

                        } else {
                            Log.w("LoginViewModel",
                                    "signInWithEmail:failure ",
                                    task.getException());
                            callback.onComplete(false, null);
                        }
                    }
                });
    }

    public void signUp(String username, String password, AuthResultCallback callback) {
        mAuth = FirebaseAuth.getInstance();
        // Checks if the username and password are not null and not empty.
        if (username != null && password != null) {
            if (username.isEmpty() || password.isEmpty() || password.length() < 6) {
                callback.onComplete(false, null);
                return;
            }
        } else {
            callback.onComplete(false, null);
            return;
        }

        // Attempts to create an account with the given username and password.
        mAuth.createUserWithEmailAndPassword(username + "@mail.com", password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                User newUser = createUser(username, password);
                                DatabaseAccess.getInstance().writeToUserDB(newUser, user -> {
                                    Log.d("LoginViewModel", "createUserAuth:success");
                                    callback.onComplete(true, newUser);
                                });
                            } else {
                                if (task
                                        .getException()
                                        instanceof FirebaseAuthUserCollisionException) {
                                    Log.w("LoginViewModel",
                                            "Email already in use by another account.");
                                } else {
                                    Log.w("LoginViewModel",
                                            "createUserAuth:failure",
                                            task.getException());
                                }
                                callback.onComplete(false, null);
                            }
                        } catch (Exception e) {
                            Log.w("LoginViewModel", "createUserAuth:failure", e);
                            callback.onComplete(false, null);
                        }

                    }
                });
        //Return true if the user is created, false otherwise
        Log.d("LoginViewModel", "signUp: " + (mAuth.getCurrentUser() != null));
    }

    public interface AuthResultCallback {
        void onComplete(boolean isSuccess, User user);
    }
}