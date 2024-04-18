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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.cs2340a_team13.AlphabeticalSortingStrategy;
import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.NumberOfIngredientsSortingStrategy;
import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.SortingStrategy;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.model.Recipe;
import com.example.cs2340a_team13.viewModels.MealViewModel;
import com.example.cs2340a_team13.viewModels.ShoppingListViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;
import java.util.ArrayList;
import com.example.cs2340a_team13.model.Ingredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import android.widget.ArrayAdapter;

public class RecipeScreen extends AppCompatActivity {
    private Button btnAddIngredient;
    private Button submitNewRecipe;
    private EditText recipeName;
    private EditText ingredientName;
    private EditText ingredientAmount;
    private AlertDialog dialog;
    private LinearLayout ingredientLayout;
    private String selected = "";

    private LinearLayout recipeListLayout;

    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    private UserViewModel userViewModel = UserViewModel.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);
        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        FloatingActionButton btnNewRecipe = (FloatingActionButton)
                findViewById(R.id.floatingAddRecipeButton);

        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);
        recipeListLayout = findViewById(R.id.recipeListLayout);

        fetchAndDisplayRecipes();

        Spinner sortingSpinner = findViewById(R.id.sortSpinner);
        ArrayList<String> spinnerItems = new ArrayList<String>();
        spinnerItems.add("Sort Alphabetically");
        spinnerItems.add("Sort by Number of Ingredients");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortingSpinner.setAdapter(adapter);

        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                if (selected.equals("Sort Alphabetically")) {
                    onSortAlphabeticallySelected();
                } else if (selected.equals("Sort by Number of Ingredients")) {
                    onSortByNumberOfIngredientsSelected();
                }

                LinearLayout recipeListLayout = findViewById(R.id.recipeListLayout);
                ArrayList<Recipe> recipesFromDB = new ArrayList<Recipe>();

                // Add data to dataList
                databaseAccess.readFromCookbookDB((queriedRecipes) -> {
                    for (Recipe recipe : queriedRecipes) {
                        recipesFromDB.add(recipe);
                    }
                });



                // Example available ingredients, replace with actual available ingredients
                List<Ingredient> pantry = UserViewModel.getInstance()
                        .getUser().getPantryIngredients();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        }


        );

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

    private ArrayList<Recipe> recipesFromDB = new ArrayList<Recipe>();
    private void fetchAndDisplayRecipes() {
        recipesFromDB.clear();
        recipeListLayout.removeAllViews();

        // Add data to dataList
        databaseAccess.readFromCookbookDB((queriedRecipes) -> {
            for (Recipe recipe : queriedRecipes) {
                recipesFromDB.add(recipe);
            }

            List<Ingredient> pantry = UserViewModel.getInstance().getUser().getPantryIngredients();

            for (Recipe recipe : recipesFromDB) {
                TextView recipeNameTextView = new TextView(this);
                boolean isEnough = recipe.isIngredientsEnough(
                        pantry, recipe.getRecipeIngredients());
                String enough = isEnough ? "Enough" : "Not Enough";
                String finalString = recipe.getRecipeName() + "\n" + enough;
                recipeNameTextView.setText(finalString);
                recipeNameTextView.setTextSize(16);
//                if (isEnough) {
//                    recipeNameTextView.setTextColor();
//                } else {
//                    recipeNameTextView.setTextColor();
//                }
                recipeNameTextView.setPadding(0, 10, 0, 10);
                //recipeListLayout.addView(recipeNameTextView); // Add the recipe name TextView

                // Create and add the "Enough" or "Not Enough" TextView
                //TextView ingredientsStatusTextView = new TextView(this);
                //boolean isEnough = recipe.isIngredientsEnough(
                // pantry, recipe.getRecipeIngredients());
                //ingredientsStatusTextView.setText(isEnough ? "Enough" : "Not Enough");
                //recipeNameTextView.setText(recipe.getRecipeName()
                // + "\n" + (isEnough ? "Enough" : "Not Enough"));
                //ingredientsStatusTextView.setTextSize(16);
                //ingredientsStatusTextView.setTextColor(isEnough ? Color.GREEN : Color.RED);
                //ingredientsStatusTextView.setPadding(0, 0, 0, 20); // Adjust padding as needed

                if (isEnough) {
                    recipeNameTextView.setTextColor(Color.rgb(14, 92, 37));
                    recipeNameTextView.setOnClickListener(v -> showRecipeDetailsPopup(recipe));
                } else {
                    recipeNameTextView.setTextColor(Color.rgb(204, 0, 0));
                    recipeNameTextView.setOnClickListener(v -> updateSLRecipeDetailsPopup(recipe));
                }
                recipeListLayout.addView(recipeNameTextView); // Add the status TextView
            }
        });

        // Example available ingredients, replace with actual available ingredients

    }

    private void showRecipeDetailsPopup(Recipe recipe) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(recipe.getRecipeName());

        // You could include more detailed information about the recipe here
        StringBuilder message = new StringBuilder();
        message.append("Description: ").append(recipe.getRecipeDescription()).append("\n\n");
        message.append("Ingredients:\n");
        for (Ingredient ingredient : recipe.getRecipeIngredients()) {
            for (Ingredient pantryIngredient : UserViewModel
                    .getInstance().getUser().getPantryIngredients()) {
                if (ingredient.getIngredientName()
                        .equalsIgnoreCase(pantryIngredient.getIngredientName())) {
                    ingredient.setCalories(pantryIngredient.getCalories());
                    message.append("- ").append(ingredient.getIngredientName())
                            .append("\n\t Quantity: ").append(ingredient.getQuantity())
                            .append(", Calories: ").append(pantryIngredient.getCalories())
                            .append(" per serving \n\t(You have ")
                            .append(pantryIngredient.getQuantity())
                            .append(")\n");
                }
            }

        }
        message.append("\nInstructions:\n").append(recipe.getRecipeInstructions()).append("\n\n");
        message.append("Calories per Serving: ")
                .append(recipe.calculateCaloriesPerServing()).append("\n");
        message.append("Cooking Time: ").append(recipe.getCookingTime()).append(" minutes");

        // Setting the message to the AlertDialog
        builder.setMessage(message.toString());

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setNegativeButton("Cook", (dialog, which) -> {
            //1. addMeal function in User
            MealViewModel.getInstance().createMeal(recipe.getRecipeName(), recipe.calculateCaloriesPerServing());
            // 3. Update calorie count
            //UserViewModel.getInstance().getUser().calculateCalories(newMeal.getCalorieCount());
            // 4. Subtract ingredients from pantry
            for (Ingredient ingredient : recipe.getRecipeIngredients()) {
                for (Ingredient pantryIngredient : UserViewModel
                        .getInstance().getUser().getPantryIngredients()) {
                    if (ingredient.getIngredientName()
                            .equalsIgnoreCase(pantryIngredient.getIngredientName())) {
                        pantryIngredient.setQuantity(pantryIngredient.getQuantity() - ingredient.getQuantity());
                        if (pantryIngredient.getQuantity() <= 0) {
                            UserViewModel.getInstance().getUser().getPantryIngredients().remove(pantryIngredient);
                        }
                    }
                }
            }
            dialog.dismiss();
            fetchAndDisplayRecipes();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //pop up for recipes with notEnough ingredients
    private void updateSLRecipeDetailsPopup(Recipe recipe) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(recipe.getRecipeName());

        StringBuilder message = new StringBuilder();
        message.append("Description: ").append(recipe.getRecipeDescription()).append("\n\n");
        message.append("Ingredients:\n");
        for (Ingredient ingredient : recipe.getRecipeIngredients()) {
            message.append("- ").append(ingredient.getIngredientName())
                    .append(", Quantity: ").append(ingredient.getQuantity())
                    .append("\n");
            boolean isFound = false;
            for (Ingredient pantryIngredient : UserViewModel
                    .getInstance().getUser().getPantryIngredients()) {
                if (ingredient.getIngredientName()
                        .equalsIgnoreCase(pantryIngredient.getIngredientName())) {
                    isFound = true;
                    ingredient.setCalories(pantryIngredient.getCalories());
                    message.append(", Calories: ").append(pantryIngredient.getCalories())
                            .append(" per serving (You have ")
                            .append(pantryIngredient.getQuantity())
                            .append(")\n");
                }
            }
            if (!isFound) {
                message.append(" (You don't have this ingredient)\n");
            }

        }
        message.append("\nInstructions:\n").append(recipe.getRecipeInstructions()).append("\n\n");
        message.append("Calories per Serving: ")
                .append(recipe.calculateCaloriesPerServing()).append("\n");
        message.append("Cooking Time: ").append(recipe.getCookingTime()).append(" minutes");

        // Setting the message to the AlertDialog
        builder.setMessage(message.toString());

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setNegativeButton("Update Shopping List", (dialog, which) ->  {
            for (Ingredient ingredient : recipe.getRecipeIngredients()) {
                ShoppingListViewModel.getInstance().addToShoppingList(ingredient.getIngredientName(), ingredient.getQuantity());
            }
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
            textView.setTextSize(16);
            textView.setPadding(0, 10, 0, 10);

            if (isEnough) {
                textView.setTextColor(Color.rgb(14, 92, 37));
                textView.setOnClickListener(v -> showRecipeDetailsPopup(recipe));
            } else {
                textView.setTextColor(Color.rgb(204, 0, 0));
                textView.setOnClickListener(v -> updateSLRecipeDetailsPopup(recipe));
            }
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
                if (ingredientName.getText().toString().isEmpty()) {
                    ingredientName.setError("Ingredient Name cannot be null");
                    ingredientName.requestFocus();
                } else if (ingredientAmount.getText().toString().isEmpty()) {
                    ingredientAmount.setError("Ingredient Amount cannot be null");
                    ingredientAmount.requestFocus();
                } else if (Integer.parseInt(ingredientAmount.getText().toString()) <= 0) {
                    ingredientAmount.setError("Ingredient Amount must be positive");
                    ingredientAmount.requestFocus();
                } else {
                    String ingredient = ingredientName.getText().toString().trim();
                    int amount = Integer.parseInt(ingredientAmount.getText().toString().trim());
                    List<Ingredient> pantry = UserViewModel
                            .getInstance().getPantryIngredientsList();
                    boolean originalRecipe = true;
                    List<Ingredient> newRecipeIngredients = newRecipe.getRecipeIngredients();
                    if (newRecipeIngredients != null) {
                        for (Ingredient item: newRecipe.getRecipeIngredients()) {
                            if (ingredient.equalsIgnoreCase(item.getIngredientName())) {
                                originalRecipe = false;
                                break;
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
                } else if (newRecipe.getRecipeIngredients() == null
                        || newRecipe.getRecipeIngredients().size() == 0) {
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
                    fetchAndDisplayRecipes();

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