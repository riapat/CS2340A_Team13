package com.example.cs2340a_team13.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.app.AlertDialog;
import android.widget.Spinner;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.model.Recipe;
import com.example.cs2340a_team13.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecipeScreen extends AppCompatActivity {
    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    private UserViewModel userViewModel = UserViewModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);

        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);
        ListView listView = findViewById(R.id.RecipeList);
        Spinner sortSpinner = findViewById(R.id.sortSpinner);

        ArrayList<String> recipeNames = new ArrayList<>();
        // Add data to dataList
        databaseAccess.readFromCookbookDB((queriedRecipes) -> {
            for (Recipe recipe : queriedRecipes) {
                recipeNames.add(recipe.getRecipeName());
            }
        });

        MyAdapterRecipe adapter = new MyAdapterRecipe(recipeNames, userViewModel.getUser().getPantryIngredients());
        listView.setAdapter(adapter);

        // Set click listener for ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked item
                String selectedItem = recipeNames.get(position);
                // Show popup
                showPopup(selectedItem);
            }
        });
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

    private void showPopup(String selectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selected Recipe");
        builder.setMessage("You clicked on: " + selectedItem);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //input recipe screen here + place text header
}