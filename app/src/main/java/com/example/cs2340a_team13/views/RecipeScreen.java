package com.example.cs2340a_team13.views;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.model.Recipe;
import com.example.cs2340a_team13.viewModels.UserViewModel;
import java.util.ArrayList;
import com.example.cs2340a_team13.model.Ingredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class RecipeScreen extends AppCompatActivity {
    private Button btnAddIngredient;
    private Button submitNewRecipe;
    private EditText recipeName;
    private EditText ingredientName;
    private EditText ingredientAmount;
    private AlertDialog dialog;
    private LinearLayout ingredientLayout;
    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    private UserViewModel userViewModel = UserViewModel.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);
        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        FloatingActionButton btnNewRecipe = (FloatingActionButton) findViewById(R.id.floatingAddRecipeButton);

        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);

        LinearLayout recipeListLayout = findViewById(R.id.recipeListLayout);
        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        ArrayList<Recipe> recipesFromDB = new ArrayList<Recipe>();

        // Add data to dataList
        databaseAccess.readFromCookbookDB((queriedRecipes) -> {
            for (Recipe recipe : queriedRecipes) {
                recipesFromDB.add(recipe);
            }
        });



        // Example available ingredients, replace with actual available ingredients
        List<Ingredient> pantry = UserViewModel.getInstance().getUser().getPantryIngredients();

        for (Recipe recipe : recipesFromDB) {
            TextView recipeNameTextView = new TextView(this);
            recipeNameTextView.setText(recipe.getRecipeName());
            recipeNameTextView.setTextSize(20);
            recipeNameTextView.setPadding(0, 10, 0, 10);
            recipeListLayout.addView(recipeNameTextView); // Add the recipe name TextView

            // Create and add the "Enough" or "Not Enough" TextView
            TextView ingredientsStatusTextView = new TextView(this);
            boolean isEnough = recipe.isIngredientsEnough(pantry, recipe.getRecipeIngredients());
            ingredientsStatusTextView.setText(isEnough ? "Enough" : "Not Enough");
            ingredientsStatusTextView.setTextSize(16);
            ingredientsStatusTextView.setTextColor(isEnough ? Color.GREEN : Color.RED);
            ingredientsStatusTextView.setPadding(0, 0, 0, 20); // Adjust padding as needed
            recipeListLayout.addView(ingredientsStatusTextView); // Add the status TextView
        }
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
        dialog = builder.create();
        dialog.show();
    }

    //input recipe screen here + place text header
    public void addNewRecipeButton(View v) {
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_add_ingredient, null);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.addIngredientLayout);

        ingredientLayout = dialogView.findViewById(R.id.addRecipeItemLayout);
        btnAddIngredient = dialogView.findViewById(R.id.addIngredientButton);
        submitNewRecipe = dialogView.findViewById(R.id.submitRecipeButton);
        ingredientName = dialogView.findViewById(R.id.addIngredientEditText);
        ingredientAmount = dialogView.findViewById(R.id.quantityEditText);
        recipeName = dialogView.findViewById(R.id.recipeNameEditText);


        // Handle New Recipe button click (create a new Alert)
        //Makes tapping outside the dialog cancel the alert

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(true);
        dialog = builder.create();
        Recipe addNewRecipe = new Recipe();

        createNewRecipe(addNewRecipe, dialogView);

        dialog.show();
    }

    private void createNewRecipe(Recipe newRecipe, View dialogView) {

        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ingredientName.getText().toString().equals("")) {
                    ingredientName.setError("Ingredient Name cannot be null");
                    ingredientName.requestFocus();
                }
                else if (ingredientAmount.getText().toString().equals("")) {
                    ingredientAmount.setError("Ingredient Amount cannot be null");
                    ingredientAmount.requestFocus();
                } else {
                    String ingredient = ingredientName.getText().toString().trim().toLowerCase();
                    int amount = Integer.parseInt(ingredientAmount.getText().toString().trim());
                    List<Ingredient> pantry = UserViewModel.getInstance().getPantryIngredientsList();
                    boolean originalRecipe = true;
                    List<Ingredient> newRecipeIngredients = newRecipe.getRecipeIngredients();
                    if (newRecipeIngredients != null) {
                        for (Ingredient item: newRecipe.getRecipeIngredients()) {
                            if (ingredient.equals(item.getIngredientName().toLowerCase())) {
                                originalRecipe = false;
                            }
                        }
                    }
                    if (originalRecipe) {

                        newRecipe.addIngredient(new Ingredient(ingredient,
                                amount, 0));
                        ingredientName.setText("");
                        ingredientAmount.setText("");
                        displayIngredients(ingredientLayout, newRecipe.getRecipeIngredients());

                    } else {
                        ingredientName.setError("Duplicates not allowed");
                        ingredientName.requestFocus();
                    }
                }

            }
        });
        submitNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipeName.getText().toString().equals("")) {
                    recipeName.setError("Recipe Name cannot be null");
                    recipeName.requestFocus();
                } else if (!(ingredientName.getText().toString().trim().equals(""))) {
                    ingredientName.setError("Field must be empty before submitting");
                    ingredientName.requestFocus();
                } else if (!(ingredientAmount.getText().toString().trim().equals(""))) {
                    ingredientAmount.setError("Field must be empty before submitting");
                    ingredientAmount.requestFocus();
                } else if (newRecipe.getRecipeIngredients() == null || newRecipe.getRecipeIngredients().size() == 0) {
                    ingredientName.setError("Recipe must have at least one ingredient");
                    ingredientName.requestFocus();
                } else {
                    String recipe = recipeName.getText().toString().trim();
                    newRecipe.setRecipeName(recipe);
                    newRecipe.setRecipeIngredients(newRecipe.getRecipeIngredients());
                    DatabaseAccess.getInstance().writeToCookbookDB(newRecipe, RecipeCallback -> {
                        if (RecipeCallback != null) {
                            recipeName.setText("");
                            ingredientName.setText("");
                            ingredientAmount.setText("");
                            dialog.dismiss();
                        } else {
                            recipeName.setError("Error adding recipe. Try Again");
                            recipeName.requestFocus();
                        }
                    });

                }
            }
        });
    }

    private void displayIngredients(LinearLayout layout, List<Ingredient> items) {
        layout.removeAllViews();
        for (Ingredient item:items) {
            String message = item.getIngredientName() + "\nQty:" + item.getQuantity();
            TextView textView = new TextView(this);
            textView.setText(message);
            textView.setTextSize(20);
            textView.setPadding(0, 10, 0, 10);
            layout.addView(textView);
        }
    }
    //input recipe screen here + place text header
}