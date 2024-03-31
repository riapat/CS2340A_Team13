package com.example.cs2340a_team13.views;
import static com.example.cs2340a_team13.viewModels.UserViewModel.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cs2340a_team13.AlphabeticalSortingStrategy;
import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.NumberOfIngredientsSortingStrategy;
import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.SortingStrategy;
import com.example.cs2340a_team13.model.Recipe;
import com.example.cs2340a_team13.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.example.cs2340a_team13.model.Ingredient;
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

    private String selected;

    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    private UserViewModel userViewModel = UserViewModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);

        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        btnNewRecipe = (FloatingActionButton) findViewById(R.id.floatingAddRecipeButton);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);
        LinearLayout recipeListLayout = findViewById(R.id.recipeListLayout);


        fetchAndDisplayRecipes();

        Spinner sortingSpinner = findViewById(R.id.sortSpinner);
        ArrayList<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add("Sort Alphabetically");
        spinnerItems.add("Sort by Number of Ingredients");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortingSpinner.setAdapter(adapter);

        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                         selected = parent.getItemAtPosition(position).toString();

                                                     }

                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> parent) {
                                                         // Do nothing
                                                     }
                                                 }


        );
        if (selected.equals("Sort Alphabetically")) {
            onSortAlphabeticallySelected();
        } else if (selected.equals("Sort by Number of Ingredients")) {
            onSortByNumberOfIngredientsSelected();
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
    LinearLayout recipeListLayout = findViewById(R.id.recipeListLayout);
    ArrayList<Recipe> recipesFromDB = new ArrayList<Recipe>();
    private void fetchAndDisplayRecipes() {

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
            boolean isEnough = recipe.isIngredientsEnough(pantry, recipe.getRecipeIngredients());
            String enough = isEnough ? "Enough" : "Not Enough";
            String finalString = recipe.getRecipeName() + "\n" + enough;
            recipeNameTextView.setText(finalString);
            recipeNameTextView.setTextSize(20);
            recipeNameTextView.setPadding(0, 10, 0, 10);
            //recipeListLayout.addView(recipeNameTextView); // Add the recipe name TextView

            // Create and add the "Enough" or "Not Enough" TextView
            //TextView ingredientsStatusTextView = new TextView(this);
            //boolean isEnough = recipe.isIngredientsEnough(pantry, recipe.getRecipeIngredients());
            //ingredientsStatusTextView.setText(isEnough ? "Enough" : "Not Enough");
            //recipeNameTextView.setText(recipe.getRecipeName() + "\n" + (isEnough ? "Enough" : "Not Enough"));
            //ingredientsStatusTextView.setTextSize(16);
            //ingredientsStatusTextView.setTextColor(isEnough ? Color.GREEN : Color.RED);
            //ingredientsStatusTextView.setPadding(0, 0, 0, 20); // Adjust padding as needed
            recipeListLayout.addView(recipeNameTextView); // Add the status TextView
        }
    }

    private void applySortingStrategy(SortingStrategy strategy) {
        strategy.sort(recipesFromDB); // Apply the sorting strategy to 'recipes'
        refreshRecipeListView(); // Refresh the display to reflect the sorted list
    }

    private void refreshRecipeListView() {
        LinearLayout recipeListLayout = findViewById(R.id.recipeListLayout);
        recipeListLayout.removeAllViews();
        // Example available ingredients, replace with actual available ingredients
        List<Ingredient> pantry = UserViewModel.getInstance().getUser().getPantryIngredients();
        for (Recipe recipe : recipesFromDB) {
            TextView textView = new TextView(this);
            boolean isEnough = recipe.isIngredientsEnough(pantry, recipe.getRecipeIngredients());
            String enough = isEnough ? "Enough" : "Not Enough";
            String finalString = recipe.getRecipeName() + "\n" + enough;
            textView.setText(finalString);
            textView.setTextSize(20);
            textView.setPadding(0, 10, 0, 10);

            recipeListLayout.addView(textView); // Add the TextView to your LinearLayout
        }
    }

    // Methods to handle user interactions that trigger sorting
    public void onSortAlphabeticallySelected() {
        applySortingStrategy(new AlphabeticalSortingStrategy());
    }

    public void onSortByNumberOfIngredientsSelected() {
        applySortingStrategy(new NumberOfIngredientsSortingStrategy());
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
    public void addNewRecipeButton(View v){
        currentRecipe = new HashMap<Ingredient, Integer>();
        // Handle New Recipe button click (create a new Alert)
        AlertDialog.Builder builder = new AlertDialog.Builder(RecipeScreen.this);
        //Makes tapping outside the dialog cancel the alert
        builder.setCancelable(true);
        LayoutInflater inflater = RecipeScreen.this.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_add_ingredient, null));
        AlertDialog dialog = builder.create();
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