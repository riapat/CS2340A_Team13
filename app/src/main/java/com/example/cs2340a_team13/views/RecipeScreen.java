package com.example.cs2340a_team13.views;
import static com.example.cs2340a_team13.viewModels.UserViewModel.user;

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

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.Recipe;
import com.example.cs2340a_team13.viewModels.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.HashMap;
import java.util.List;

public class RecipeScreen extends AppCompatActivity {
    FloatingActionButton btnNewRecipe;
    Button btnAddIngredient;
    Button submitNewRecipe;
    EditText recipeName;
    EditText ingredientName;
    EditText ingredientAmount;
    HashMap<Ingredient, Integer> currentRecipe;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);
        Button btnRecipe = findViewById(R.id.Recipe);
        btnNewRecipe = (FloatingActionButton) findViewById(R.id.floatingAddRecipeButton);
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
        currentRecipe = new HashMap<Ingredient, Integer>();
        // Handle New Recipe button click (create a new Alert)
        AlertDialog.Builder builder = new AlertDialog.Builder(RecipeScreen.this);
        //Makes tapping outside the dialog cancel the alert
        builder.setCancelable(true);
        LayoutInflater inflater = RecipeScreen.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_add_ingredient, null);
        dialog = builder.create();
        dialog.show();
    }

    public void submitNewRecipe(View v){
        if (recipeName.getText() == null) {
            recipeName.setError("Recipe Name cannot be null");
            recipeName.requestFocus();
        }else if(ingredientName.getText() != null) {
            ingredientName.setError("Field must be empty before submitting");
            ingredientName.requestFocus();
        }else if (ingredientAmount.getText() == null) {
            ingredientAmount.setError("Field must be empty before submitting");
            ingredientAmount.requestFocus();
        }else{
            String recipe = recipeName.getText().toString().trim();
            Recipe newRecipe = new Recipe(recipe,currentRecipe);
            DatabaseAccess.writeToCookbookDB(newRecipe,RecipeCallback->{
                if(RecipeCallback){
                    recipeName.setText("");
                    ingredientName.setText("");
                    ingredientAmount.setText("");
                    dialog.dismiss();
                    currentRecipe = null;
                }else{
                    recipeName.setError("Error adding recipe. Try Again");
                    recipeName.requestFocus();
                }
            });

        }
    }
    public void addNewIngredient(View v){
        if (ingredientName.getText() == null) {
            ingredientName.setError("Ingredient Name cannot be null");
            ingredientName.requestFocus();
            return;
        }
        if (ingredientAmount.getText() == null) {
            ingredientAmount.setError("Ingredient Amount cannot be null");
            ingredientAmount.requestFocus();
            return;
        }

        String ingredient = ingredientName.getText().toString().trim();
        int amount = Integer.parseInt(ingredientAmount.getText().toString().trim());
        List<Ingredient> pantry = UserViewModel.getInstance().getPantryIngredientsList();
        pantry.get(pantry.size()-1);
        for(Ingredient item:pantry){
            if(ingredient.equals(item.getIngredientName()) {
                if(currentRecipe.get(item) == null){
                    currentRecipe.put(item, amount);
                    ingredientName.setText("");
                    ingredientAmount.setText("");
                }else{
                    ingredientName.setError("Duplicates not allowed");
                    ingredientName.requestFocus();
                }
                return;
            }
        }
        ingredientName.setError("Ingredient Doesn't Exist");
        ingredientName.requestFocus();
    }


     //input recipe screen here + place text header
}