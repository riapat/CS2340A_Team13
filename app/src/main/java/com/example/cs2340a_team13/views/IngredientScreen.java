package com.example.cs2340a_team13.views;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.viewModels.IngredientViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;
import java.util.List;



public class IngredientScreen extends AppCompatActivity {
    private IngredientViewModel ingredientInstance = IngredientViewModel.getInstance();

    private UserViewModel userViewModel = UserViewModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_screen);
        displayPantryIngredients();
        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);
        Button btnSubmitIngredient = findViewById(R.id.submitButton);
        EditText ingredientNameEditText = findViewById(R.id.ingredientNameEditText);
        EditText quantityEditText = findViewById(R.id.quantityEditText);
        EditText caloriesEditText = findViewById(R.id.caloriesEditText);
        EditText expirationDateEditText = findViewById(R.id.expirationDateEditText);
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

        IngredientViewModel ingredientViewModel = IngredientViewModel.getInstance();

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

        btnSubmitIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientName = ingredientNameEditText.getText().toString().trim();
                String quantityText = quantityEditText.getText().toString().trim();
                String caloriesText = caloriesEditText.getText().toString().trim();
                String expirationDate = expirationDateEditText.getText().toString().trim();

                if (ingredientName.isEmpty() || quantityText.isEmpty() || caloriesText.isEmpty()) {
                    showAlert("Please fill in all fields.");
                    return;
                }
                if (expirationDate.isEmpty()) {
                    expirationDate = "";
                }
                int quantity;
                int calories;
                try {
                    quantity = Integer.parseInt(quantityText);
                    calories = Integer.parseInt(caloriesText);
                    if (quantity <= 0 || calories <= 0) {
                        showAlert("Quantity and Calories must be positive integers.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Quantity and Calories must be integers.");
                    return;
                }

                if (!ingredientName.matches("[a-zA-Z ]+")) {
                    showAlert("Ingredient name must not contain numbers.");
                    return;
                }
                if (ingredientViewModel
                        .checkDuplicate(
                                new Ingredient(ingredientName, quantity, calories, expirationDate)
                        )) {
                    showAlert("Ingredient already exists in pantry with nonzero quantity.");
                    return;
                }
                Ingredient ingredient = ingredientViewModel
                        .createIngredient(ingredientName, quantity, calories, expirationDate);
                ingredientNameEditText.setText("");
                quantityEditText.setText("");
                caloriesEditText.setText("");
                expirationDateEditText.setText("");
                findViewById(R.id.ingredientNameEditText).setVisibility(View.GONE);
                findViewById(R.id.quantityEditText).setVisibility(View.GONE);
                findViewById(R.id.caloriesEditText).setVisibility(View.GONE);
                findViewById(R.id.expirationDateEditText).setVisibility(View.GONE);
                findViewById(R.id.submitButton).setVisibility(View.GONE);

                displayPantryIngredients();



            }
        });


        //pop up for adjust ingredients button
        Button btnAdjustIngredients = findViewById(R.id.adjustIngredientsButton);
        btnAdjustIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a form for adjusting ingredients
                showAdjustIngredientsDialog();
            }
        });
    }

    private void showAdjustIngredientsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adjust Ingredients");

        final EditText inputIngredName = new EditText(this);
        inputIngredName.setHint("Ingredient Name");

        final EditText inputQuant = new EditText(this);
        inputQuant.setHint("Quantity");

        //buttons
        builder.setPositiveButton("Add", null);
        builder.setNeutralButton("Subtract", null);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
        });

        //input fields in layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(inputIngredName);
        layout.addView(inputQuant);
        builder.setView(layout);

        // Create and show the dialog
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button addButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ingredName = inputIngredName.getText().toString().trim();
                        String quantStr = inputQuant.getText().toString().trim();
                        //addition logic
                        addIngredientQuantity(ingredName, quantStr);
                        dialog.dismiss();
                    }
                });

                Button removeButton = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String ingredName = inputIngredName.getText().toString().trim();
                        String quantStr = inputQuant.getText().toString().trim();
                        //removal logic
                        decreaseIngredientQuantity(ingredName, quantStr);
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }


    private void addIngredientQuantity(String ingredientName, String quantityStr) {
        //quant string to int
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number.");
            return;
        }
        if (!ingredientName.matches("[a-zA-Z ]+")) {
            showAlert("Ingredient name must not contain numbers.");
            return;
        }
        if (!ingredientInstance.existingIngredient(ingredientName)) {
            showAlert("Ingredient doesn't exist in pantry.");
            return;
        }
        //logic to add
        ingredientInstance.increaseIngredient(ingredientName, quantity);
        displayPantryIngredients();
    }

    private void decreaseIngredientQuantity(String ingredientName, String quantityStr) {
        //quant string to int
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number.");
            return;
        }
        System.out.println("Going to decreaseIngredient View Model");
        //logic to remove
        ingredientInstance.decreaseIngredient(ingredientName, quantity);
        displayPantryIngredients();
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void displayPantryIngredients() {
        LinearLayout pantryLinearLayout = findViewById(R.id.pantryLinearLayout);
        pantryLinearLayout.removeAllViews(); // Clear previous views

        List<Ingredient> pantryIngredients = UserViewModel
                .getInstance().getUser().getPantryIngredients();
        if (pantryIngredients != null) {
            for (Ingredient ingredient : pantryIngredients) {
                //textView for each ingredient
                TextView textView = new TextView(this);
                textView.setText(ingredient.getIngredientName() + ": " + ingredient.getQuantity());
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                pantryLinearLayout.addView(textView);
            }
        }
    }
}

