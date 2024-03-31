package com.example.cs2340a_team13.views;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.cs2340a_team13.R;

public class RecipeScreen extends AppCompatActivity {
    Button btnNewRecipe;
    Button btnAddIngredient;
    Button submitNewRecipe;
    EditText recipeName;
    EditText ingredientName;
    EditText ingredientAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);
        Button btnRecipe = findViewById(R.id.Recipe);
        btnNewRecipe = findViewById(R.id.floatingAddRecipeButton);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);

        btnInputMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle input meal button click (navigate to input meal screen)
                Intent intent = new Intent(RecipeScreen.this, InputMealScreen.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        btnIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeScreen.this, IngredientScreen.class);
                startActivity(intent);
            }
        });
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle shopping list button click (navigate to shopping list screen)
                Intent intent = new Intent(RecipeScreen.this, ShoppingListScreen.class);
                startActivity(intent);
            }
        });

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle personal info button click (navigate to personal info screen)
                Intent intent = new Intent(RecipeScreen.this, PersonalInformation.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("InflateParams")
    public void addNewRecipeButton(View v){
        // Handle New Recipe button click (create a new Alert)
        AlertDialog.Builder builder = new AlertDialog.Builder(RecipeScreen.this);
        //Makes tapping outside the dialog cancel the alert
        builder.setCancelable(true);
        LayoutInflater inflater = RecipeScreen.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_add_ingredient, null);
        builder.create();
    }

    public void addNewIngredient(View V){
    }

     //input recipe screen here + place text header
}