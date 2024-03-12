package com.example.cs2340a_team13.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.viewModels.UserViewModel;

public class HomeScreen extends AppCompatActivity {

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        userViewModel = UserViewModel.getInstance();
        String username = getIntent().getStringExtra("username");
        userViewModel.loadUser(username);
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
                Intent intent = new Intent(HomeScreen.this, InputMealScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle recipe button click (navigate to recipe screen)
                Intent intent = new Intent(HomeScreen.this, RecipeScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        btnIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle ingredients button click (navigate to ingredients screen)
                Intent intent = new Intent(HomeScreen.this, IngredientScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle shopping list button click (navigate to shopping list screen)
                Intent intent = new Intent(HomeScreen.this, ShoppingListScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, PersonalInformation.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }
}