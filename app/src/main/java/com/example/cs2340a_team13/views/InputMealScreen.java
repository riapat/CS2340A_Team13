package com.example.cs2340a_team13.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.viewModels.MealViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;

import java.util.Date;

public class InputMealScreen extends AppCompatActivity {

    private MealViewModel mealViewModel;
    private UserViewModel userViewModel;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView ageTextView = findViewById(R.id.ageTextView);
        TextView genderTextView = findViewById(R.id.genderTextView);
        TextView heightTextView = findViewById(R.id.heightTextView);
        TextView weightTextView = findViewById(R.id.weightTextView);
        TextView recommendedCaloriesTextView = findViewById(R.id.recommendedCaloriesTextView);
        TextView currentCaloriesTextView = findViewById(R.id.currentCaloriesTextView);

        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        EditText mealNameEditText = findViewById(R.id.mealNameEditText);
        EditText caloriesEditText = findViewById(R.id.estimatedCaloriesEditText);
        Button submitButton = findViewById(R.id.submitButton);

        mealViewModel = MealViewModel.getInstance();
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);

        userViewModel = UserViewModel.getInstance();
        String username = getIntent().getStringExtra("username");
        userViewModel.loadUser(username);
        Log.d("InputMealScreen", "User loaded in input meal screen " + userViewModel.getUser().getUsername());

        if(userViewModel.getUser() == null) {
            ageTextView.setText("Age:  yrs");
            genderTextView.setText("Gender: ");
            heightTextView.setText("Height:  cm");
            weightTextView.setText("Weight:  kg");
            recommendedCaloriesTextView.setText("Advised Daily Calories: 0");
            currentCaloriesTextView.setText("Current Day's Calories: 0");
        }else{
            ageTextView.setText(String.format("Age: %d yrs",
                    userViewModel.getUser().getAge()));
            genderTextView.setText(String.format("Gender: %s",
                    userViewModel.getUser().getGender()));
            heightTextView.setText(String.format("Height: %d cm",
                    userViewModel.getUser().getHeight()));
            weightTextView.setText(String.format("Weight: %d kg",
                    userViewModel.getUser().getWeight()));
            recommendedCaloriesTextView.setText(String.format("Advised Daily Calories: %f",
                    userViewModel.calculateCalories(userViewModel)));
            currentCaloriesTextView.setText(String.format("Current Day's Calories: %f", 0.0));
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealScreen.this, HomeScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle recipe button click (navigate to recipe screen)
                Intent intent = new Intent(InputMealScreen.this, RecipeScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        btnIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle ingredients button click (navigate to ingredients screen)
                Intent intent = new Intent(InputMealScreen.this, IngredientScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle shopping list button click (navigate to shopping list screen)
                Intent intent = new Intent(InputMealScreen.this, ShoppingListScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = mealNameEditText.getText().toString();
                String caloriesText = caloriesEditText.getText().toString();

                if (!caloriesText.isEmpty()) {
                    try {
                        int calories = Integer.parseInt(caloriesText);
                        Meal meal = mealViewModel.createMeal(mealName, calories);
                    } catch (NumberFormatException e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(InputMealScreen.this);
                        builder.setTitle("Invalid Input");
                        builder.setMessage("Calories must be a number");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InputMealScreen.this);
                    builder.setTitle("Invalid Input");
                    builder.setMessage("Please enter calories");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }
        });

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealScreen.this, PersonalInformation.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}