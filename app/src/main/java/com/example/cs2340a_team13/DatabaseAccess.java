package com.example.cs2340a_team13;
import com.example.cs2340a_team13.model.User;
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

    //Write to "Users" table by sending in a User object
    public void writeToUserDB(User user) {
        fbInstance.getReference("Users").child(user.getUsername()).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("User added to user database");
                    } else {
                        System.out.println("User not added to user database");
                    }
                });
    }

    //Query from "Users" table by sending in a username
    public User readFromUserDB(String username) {
        User user = new User();
        fbInstance.getReference("Users").child(username).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.setUser(task.getResult().getValue(User.class));
                    } else {
                        System.out.println("User not found in user database");
                    }
                });
        return user;
    }

    //Update "Users" table by sending in a User object
    public void updateToUserDB(User user) {
            fbInstance.getReference("Users").child(user.getUsername()).setValue(user)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            System.out.println("User updated in user database");
                        } else {
                            System.out.println("User not updated in user database");
                        }
                    });
    }

    //Write to Meals table by sending in a Meal object
    public void writeToMealsDB(Meal meal) {
        fbInstance.getReference("Meals").child(meal.getMealName()).setValue(meal)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Meal added to meals database");
                    } else {
                        System.out.println("Meal not added to meals database");
                    }
                });
    }

    //Query from Meals table by sending in a meal name
    public Meal readFromMealsDB(String mealName) {
        Meal meal = new Meal();
        fbInstance.getReference("Meals").child(mealName).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        meal.setMeal(task.getResult().getValue(Meal.class));
                    } else {
                        System.out.println("Meal not found in meals database");
                    }
                });
        return meal;
    }

    //Update Meals table by sending in a Meal object
    public void updateToMealsDB(Meal meal) {
        fbInstance.getReference("Meals").child(meal.getMealName()).setValue(meal)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Meal updated in meals database");
                    } else {
                        System.out.println("Meal not updated in meals database");
                    }
                });
    }

}
