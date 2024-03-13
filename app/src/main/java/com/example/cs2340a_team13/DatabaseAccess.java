package com.example.cs2340a_team13;
import android.util.Log;

import com.example.cs2340a_team13.model.User;
import com.example.cs2340a_team13.model.Meal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseAccess {

    private FirebaseDatabase fbInstance;
    private static DatabaseAccess databaseInstance;

    private DatabaseAccess(FirebaseDatabase fbInstance) {
        this.fbInstance = fbInstance;
    }

    public static synchronized DatabaseAccess getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new DatabaseAccess(FirebaseDatabase.getInstance()); // Removed the redeclaration
        }
        return databaseInstance;
    }

    public interface UserCallback {
        void onComplete(User user);
    }

    //Write to "Users" table by sending in a User object
    //Has a callback function to turn this function to become synchronous
    public void writeToUserDB(User user, UserCallback callback) {
        fbInstance.getReference("Users").child(user.getUsername()).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("User added to user database");
                        callback.onComplete(user);
                    } else {
                        System.out.println("User not added to user database");
                        callback.onComplete(null);
                    }
                });
    }

    //Query from "Users" table by sending in a username
    public void readFromUserDB(String username, UserCallback callback) {
        User user = new User();
        DatabaseReference userRef = fbInstance.getReference("Users").child(username);
        userRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("DatabaseAccess", "User found in user database " + task.getResult().getValue(User.class).getUsername());
                        user.setUser(task.getResult().getValue(User.class));

                        DatabaseReference mealRef = userRef.child("meals");
                        mealRef.get()
                                .addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        for (DataSnapshot mealSnapshot : task2.getResult().getChildren()) {
                                            Meal meal = mealSnapshot.getValue(Meal.class);
                                            if (meal != null) {
                                                user.addMeal(meal); // Add each meal to the User's loggedMeals list
                                                Log.i("DatabaseAccess", "Meal found in user database " + mealSnapshot.getValue(Meal.class).getMealName());
                                            }
                                        }
                                        // Callback after all meals have been added to the User object
                                        callback.onComplete(user);
                                    } else {
                                        System.out.println("Meals not found in user database");
                                        callback.onComplete(null);
                                    }
                                });
                    } else {
                        System.out.println("User not found in user database");
                        callback.onComplete(null);
                    }
                });
    }

    //Update "Users" table by sending in a User object
    public void updateToUserDB(User user, UserCallback callback) {
            fbInstance.getReference("Users").child(user.getUsername()).setValue(user)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            System.out.println("User updated in user database");
                            callback.onComplete(user);
                        } else {
                            System.out.println("User not updated in user database");
                            callback.onComplete(null);
                        }
                    });
    }

    public interface MealCallback {
        void onComplete(Meal meal);
    }
    //Write to Meals table by sending in a Meal object
    public void writeToMealsDB(Meal meal, MealCallback callback) {
        fbInstance.getReference("Meals").child(meal.getMealName()).setValue(meal)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Meal added to meals database");
                        callback.onComplete(meal);
                    } else {
                        System.out.println("Meal not added to meals database");
                        callback.onComplete(null);
                    }
                });
    }

    //Query from Meals table by sending in a meal name
    public Meal readFromMealsDB(String mealName, MealCallback callback) {
        Meal meal = new Meal();
        fbInstance.getReference("Meals").child(mealName).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        meal.setMeal(task.getResult().getValue(Meal.class));
                        System.out.println("Meal found in meals database");
                        callback.onComplete(meal);
                    } else {
                        System.out.println("Meal not found in meals database");
                        callback.onComplete(null);
                    }
                });
        return meal;
    }

    //Update Meals table by sending in a Meal object
    public void updateToMealsDB(Meal meal, MealCallback callback) {
        fbInstance.getReference("Meals").child(meal.getMealName()).setValue(meal)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Meal updated in meals database");
                        callback.onComplete(meal);
                    } else {
                        System.out.println("Meal not updated in meals database");
                        callback.onComplete(null);
                    }
                });
    }

}
