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
    private static User user;
    private static UserViewModel instance;

    public UserViewModel() {
        user = null;
    }

    public static synchronized UserViewModel getInstance() {
        if (instance == null) {
            instance = new UserViewModel();
        }
        return instance;
    }

    public void loadUser(String username) {
        if (user == null) {
            DatabaseAccess.getInstance().readFromUserDB(username, userCallback -> {
                if (userCallback != null) {
                    user = userCallback;
                    Log.d("UserViewModel", "User found in user database " + user.getPassword());
                } else {
                    System.out.println("User not found in user database");
                }
            });
        } else {
            Log.d("UserViewModel", "User already loaded in user view model " + user.getPassword());
        }


    }

    public User getUser() {
        return user;
    }

    public void updateUserHeight(int height) {
        this.user.setHeight(height);
        DatabaseAccess.getInstance().updateToUserDB(user, userCallback -> {
            if (userCallback != null) {
                System.out.println("User updated in user database");
            } else {
                System.out.println("User not updated in user database");
            }
        });
    }

    public void updateUserWeight(int weight) {
        this.user.setWeight(weight);
        DatabaseAccess.getInstance().updateToUserDB(user, userCallback -> {
            if (userCallback != null) {
                System.out.println("User updated in user database");
            } else {
                System.out.println("User not updated in user database");
            }
        });
    }

    public void updateUserGender(String gender) {
        this.user.setGender(gender);
        DatabaseAccess.getInstance().updateToUserDB(user, userCallback -> {
            if (userCallback != null) {
                System.out.println("User updated in user database");
            } else {
                System.out.println("User not updated in user database");
            }
        });
    }

    public void updateUserAge(int age) {
        this.user.setAge(age);
        DatabaseAccess.getInstance().updateToUserDB(user, userCallback -> {
            if (userCallback != null) {
                System.out.println("User updated in user database");
            } else {
                System.out.println("User not updated in user database");
            }
        });
    }
    public double calculateCalories(UserViewModel userVM){
        if(userVM.getUser() == null){
            return 0;
        }else {
            double age = (double) userVM.getUser().getAge();
            String gender =  userVM.getUser().getGender();
            double weight = (double) userVM.getUser().getWeight();
            double height = (double) userVM.getUser().getHeight();
            double idealCal = 0;
            switch(gender.toLowerCase()){
                case "female":
                case  "woman":
                case "f":
                case "w":
                    idealCal = 1.2 * ((10.0 * weight) + (height * 6.25) - (5.0 * age) + 5.0);
                    break;
                case "male":
                case  "man":
                case "m":
                    idealCal = 1.2 * (10.0 * weight) + (height * 6.25) - (5.0 * age) - 161.0;
                    break;
                default:
                    idealCal = 0;
                    break;
            }
            return idealCal;
        }
    }
}