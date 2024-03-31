package com.example.cs2340a_team13.views;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.viewModels.IngredientViewModel;


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
        Button btnSubmitIngredient = findViewById(R.id.submitButton);
        EditText ingredientNameEditText = findViewById(R.id.ingredientNameEditText);
        EditText  quantityEditText = findViewById(R.id.quantityEditText);
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
                if (expirationDate.isEmpty()){
                    expirationDate = "";
                }

                int quantity, calories;
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
                if (ingredientViewModel.checkDuplicate(new Ingredient(ingredientName, quantity, calories, expirationDate))) {
                    showAlert("Ingredient already exists in pantry with nonzero quantity.");
                    return;
                }
                Ingredient ingredient = ingredientViewModel.createIngredient(ingredientName, quantity, calories, expirationDate);
                ingredientNameEditText.setText("");
                quantityEditText.setText("");
                caloriesEditText.setText("");
                expirationDateEditText.setText("");

            }
        });
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

    }

