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
import com.example.cs2340a_team13.viewModels.ShoppingListViewModel;

public class ShoppingListScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_screen);
        EditText ingredientNameEditText = findViewById(R.id.ingredientNameEditText);
        EditText quantityEditText = findViewById(R.id.quantityEditText);
        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);
        Button btnSubmit = findViewById(R.id.submitSlButton);
        Button btnaddItem = findViewById(R.id.addItemButton);
        Button btnCancelItem = findViewById(R.id.cancelButton1);

        btnInputMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle input meal button click (navigate to input meal screen)
                Intent intent = new Intent(ShoppingListScreen.this, InputMealScreen.class);
                startActivity(intent);
            }
        });
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle recipe button click (navigate to recipe screen)
                Intent intent = new Intent(ShoppingListScreen.this, RecipeScreen.class);
                startActivity(intent);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientName = ingredientNameEditText.getText().toString().trim();
                String quantityText = quantityEditText.getText().toString().trim();
                if (ingredientName.isEmpty() || quantityText.isEmpty()) {
                    showAlert("Please fill in all fields.");
                    return;
                }
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityText);
                    if (quantity <= 0) {
                        showAlert("Quantity must be a positive integer.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Quantity must be an integer.");
                    return;
                }

                if (!ingredientName.matches("[a-zA-Z ]+")) {
                    showAlert("Ingredient name must not contain numbers.");
                    return;
                }

                // Call the addToShoppingList method
                ShoppingListViewModel.getInstance().addToShoppingList(ingredientName, quantity);

                ingredientNameEditText.setText("");
                quantityEditText.setText("");
            }
        });
        btnaddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set visibility of other text fields to VISIBLE
                findViewById(R.id.ingredientNameEditText).setVisibility(View.VISIBLE);
                findViewById(R.id.quantityEditText).setVisibility(View.VISIBLE);
                findViewById(R.id.submitButton).setVisibility(View.VISIBLE);
                findViewById(R.id.cancelButton).setVisibility(View.VISIBLE);
                ((EditText) findViewById(R.id.ingredientNameEditText)).setText("");
                ((EditText) findViewById(R.id.quantityEditText)).setText("");
            }
        });
        btnCancelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set visibility of other text fields to VISIBLE
                findViewById(R.id.ingredientNameEditText).setVisibility(View.GONE);
                findViewById(R.id.quantityEditText).setVisibility(View.GONE);
                findViewById(R.id.submitButton).setVisibility(View.GONE);
                findViewById(R.id.cancelButton).setVisibility(View.GONE);
            }
        });


        btnIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle ingredients button click (navigate to ingredients screen)
                Intent intent = new Intent(ShoppingListScreen.this, IngredientScreen.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingListScreen.this, PersonalInformation.class);
                startActivity(intent);
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
    //input shopping screen here + place text header
}