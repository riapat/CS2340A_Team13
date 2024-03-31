package com.example.cs2340a_team13.viewModels;
import android.annotation.SuppressLint;
import android.util.Log;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.User;


import java.util.ArrayList;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.example.cs2340a_team13.model.Ingredient;

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
                    DatabaseAccess.getInstance().loadPantry(username, ingredients -> {
                        if (ingredients != null) {
                            Log.d("Ingredients", String.valueOf(ingredients.size()));
                            // If pantry loaded successfully, update user's pantry and complete sign-in
                            userCallback.setPantryIngredients(ingredients); // Ensure User model has a method to set pantry
                            user = userCallback;
                            Log.d("UserViewModel", "User found in user database " + user.getPassword());
                        } else {
                            // Handle failure to load pantry here, if necessary
                            Log.w("LoginViewModel", "Failed to load pantry");
                        }
                    });

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
    public double calculateCalories() {
        if (user == null) {
            return 0;
        } else {
            double age = (double) user.getAge();
            String gender =  user.getGender();
            double weight = (double) user.getWeight();
            double height = (double) user.getHeight();
            double idealCal = 0;
            switch (gender.toLowerCase()) {
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
    public int currentCalories() {
        if (user == null) {
            return 0;
        }
        int currentCal = 0;
        List<Meal> meals = user.getMeals();
        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String today = dateFormat.format(currentDate);
        String currentMealDate = "";
        for (Meal meal:meals) {
            currentMealDate = dateFormat.format(meal.getDate());
            if (today.equals(currentMealDate)) {
                currentCal += meal.getCalorieCount();
            }
        }
        return currentCal;
    }

    public List<DataEntry> calculateMonthlyCalories() {
        int[] monthlyCalories = new int[12];

        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());

        for (Meal meal: user.getMeals()) {
            String month = monthFormat.format(meal.getDate());
            int monthIndex = Integer.parseInt(month) - 1;
            monthlyCalories[monthIndex] += meal.getCalorieCount();
        }

        List<DataEntry> dataEntries = new ArrayList<>();
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (int i = 0; i < 12; i++) {
            dataEntries.add(new ValueDataEntry(monthNames[i], monthlyCalories[i]));

        }
        return dataEntries;
    }

    public List<Ingredient> getPantryIngredientsList() {
        if (user != null) {
            return user.getPantryIngredients();
        }
        return null;
    }

    //DO NOT USE - Testing Purposes Only
    public void setTestUser(User testUser) {
        this.user = testUser;
    }
}