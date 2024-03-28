package com.example.cs2340a_team13.views;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.cs2340a_team13.R;


public class IngredientScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_screen);


        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);


        btnInputMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle input meal button click (navigate to input meal screen)
                Intent intent = new Intent(IngredientScreen.this, InputMealScreen.class);
                startActivity(intent);
            }
        });
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle recipe button click (navigate to recipe screen)
                Intent intent = new Intent(IngredientScreen.this, RecipeScreen.class);
                startActivity(intent);
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(IngredientScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle shopping list button click (navigate to shopping list screen)
                Intent intent = new Intent(IngredientScreen.this, ShoppingListScreen.class);
                startActivity(intent);
            }
        });
        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle personal info button click (navigate to personal info screen)
                Intent intent = new Intent(IngredientScreen.this, PersonalInformation.class);
                startActivity(intent);
            }
        });


        // Find the input ingredients button
        Button btnInputIngredients = findViewById(R.id.InputIngredients);


        // Set onClick listener for input ingredients button
        btnInputIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set visibility of other text fields to VISIBLE
                findViewById(R.id.ingredientNameEditText).setVisibility(View.VISIBLE);
                findViewById(R.id.quantityEditText).setVisibility(View.VISIBLE);
                findViewById(R.id.caloriesEditText).setVisibility(View.VISIBLE);
                findViewById(R.id.expirationDateEditText).setVisibility(View.VISIBLE);
                findViewById(R.id.submitButton).setVisibility(View.VISIBLE);
                ((EditText) findViewById(R.id.ingredientNameEditText)).setText("");
                ((EditText) findViewById(R.id.quantityEditText)).setText("");
                ((EditText) findViewById(R.id.caloriesEditText)).setText("");
                ((EditText) findViewById(R.id.expirationDateEditText)).setText("");
            }
        });
    }
}
