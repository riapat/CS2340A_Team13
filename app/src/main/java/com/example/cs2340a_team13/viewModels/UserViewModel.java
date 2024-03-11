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
import com.google.firebase.database.core.DatabaseConfig;

public class UserViewModel {
    private User user;
    private static UserViewModel instance;

    public UserViewModel() {
        user = new User();
    }

    public static synchronized UserViewModel getInstance() {
        if (instance == null) {
            instance = new UserViewModel();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void updateUserHeight(int height) {
        this.user.setHeight(height);
        DatabaseAccess.getInstance().updateToUserDB(user);
    }

    public void updateUserWeight(int weight) {
        this.user.setWeight(weight);
        DatabaseAccess.getInstance().updateToUserDB(user);
    }

    public void updateUserGender(String gender) {
        this.user.setGender(gender);
        DatabaseAccess.getInstance().updateToUserDB(user);
    }

    public void updateUserAge(int age) {
        this.user.setAge(age);
        DatabaseAccess.getInstance().updateToUserDB(user);
    }
}
