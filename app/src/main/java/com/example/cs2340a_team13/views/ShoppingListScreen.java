package com.example.cs2340a_team13.views;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.model.Ingredient;
import java.util.ArrayList;
import java.util.List;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.cs2340a_team13.viewModels.IngredientViewModel;
import com.example.cs2340a_team13.viewModels.ShoppingListViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;

public class ShoppingListScreen extends AppCompatActivity {
    private List<CheckBox> shoppingListItems = new ArrayList<CheckBox>();
    private Button buySelectedIngredients;
    private LinearLayout cartLayout;
    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();
    private UserViewModel userViewModel = UserViewModel.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_screen);
        EditText ingredientNameEditText = findViewById(R.id.ingredientNameEditText);
        EditText quantityEditText = findViewById(R.id.quantityEditText);
        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnHome = findViewById(R.id.Home);
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);
        Button btnSubmit = findViewById(R.id.submitSlButton);
        EditText caloriesInput = findViewById(R.id.caloriesEditText);
        buySelectedIngredients = findViewById(R.id.purchaseCart);
        cartLayout = findViewById(R.id.shoppingListLayout);
        Button btnaddItem = findViewById(R.id.addItemButton);
        Button btnCancelItem = findViewById(R.id.cancelButton1);
        updateCart();

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

        btnaddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set visibility of other text fields to VISIBLE
                ingredientNameEditText.setVisibility(View.VISIBLE);
                quantityEditText.setVisibility(View.VISIBLE);
                caloriesInput.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                btnSubmit.setEnabled(true);
                btnCancelItem.setVisibility(View.VISIBLE);
                btnCancelItem.setEnabled(true);
                ingredientNameEditText.setText("");
                quantityEditText.setText("");
                caloriesInput.setText("");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Submit button clicked");
                String ingredientName = ingredientNameEditText.getText().toString().trim();
                String quantityText = quantityEditText.getText().toString().trim();
                String caloriesText = caloriesInput.getText().toString().trim();
                if (ingredientName.isEmpty() || quantityText.isEmpty() || caloriesText.isEmpty()) {
                    showAlert("Please fill in all fields.");
                    return;
                }
                int quantity;
                int calories;
                try {
                    quantity = Integer.parseInt(quantityText);
                    if (quantity <= 0) {
                        showAlert("Quantity must be a positive integer.");
                        return;
                    }
                    calories = Integer.parseInt(caloriesText);
                    if (calories < 0) {
                        showAlert("Calories must be a nonnegative integer.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Quantity/Calories per serving must be an integer.");
                    return;
                }

                if (!ingredientName.matches("[a-zA-Z ]+")) {
                    showAlert("Ingredient name must not contain numbers.");
                    return;
                }

                // Call the addToShoppingList method
                ShoppingListViewModel.getInstance()
                        .addToShoppingList(ingredientName, quantity, calories);
                updateCart();
                ingredientNameEditText.setText("");
                quantityEditText.setText("");
                caloriesInput.setText("");
            }
        });

        btnCancelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set visibility of other text fields to GONE
                ingredientNameEditText.setVisibility(View.GONE);
                quantityEditText.setVisibility(View.GONE);
                caloriesInput.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                btnCancelItem.setVisibility(View.GONE);
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
    private void updateCart() {
        shoppingListItems.clear();
        if (cartLayout.getChildCount() > 0) {
            cartLayout.removeAllViews();
        }
        databaseAccess.loadShoppingList(UserViewModel.getInstance().getUser().getUsername(),
                (shoppingCart) -> {
                if (shoppingCart.isEmpty()) {
                    TextView textView = new TextView(this);
                    textView.setText("Your Shopping Cart is Empty");
                    cartLayout.addView(textView);
                } else {
                    for (Ingredient cartItem:shoppingCart) {
                        CheckBox item = new CheckBox(this);
                        String name = cartItem.getIngredientName()
                                + "\n Quantity: " + cartItem.getQuantity()
                                + "\n Calories: " + cartItem.getCalories();
                        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                        item.setText(name);
                        item.setChecked(false);
                        shoppingListItems.add(item);
                        cartLayout.addView(item);
                    }
                }
            });
    }
    public void buySelectedIngredientsOnClick(View v) {
        databaseAccess.loadShoppingList(UserViewModel.getInstance().getUser().getUsername(),
                (shoppingCart) -> {
                List<Ingredient> selectedItems = new ArrayList<Ingredient>();
                if (shoppingCart.isEmpty()) {
                    showAlert("Cannot Buy Items: \n Cart is Empty");
                } else {
                    for (int i = 0; i < cartLayout.getChildCount(); i++) {
                        View view = cartLayout.getChildAt(i);
                        if (view instanceof CheckBox) {
                            CheckBox currentCheckbox = (CheckBox) view;
                            String[] itemInfo = currentCheckbox.getText().toString().split("\\R");
                            Ingredient itemSelected = null;
                            for (Ingredient shoppingCartItem: shoppingCart) {
                                String itemName = shoppingCartItem.getIngredientName();
                                if (itemInfo[0].equalsIgnoreCase(itemName)) {
                                    if (currentCheckbox.isChecked()) {
                                        selectedItems.add(shoppingCartItem);
                                        itemSelected = shoppingCartItem;
                                    }

                                    break;
                                }
                            }
                            if (itemSelected != null) {
                                shoppingCart.remove(itemSelected);
                            }
                        }
                    }
                    UserViewModel.getInstance().updateUserShoppingCart(shoppingCart);
                    IngredientViewModel.getInstance().addIngredients(selectedItems);
                    updateCart();
                    if (selectedItems.isEmpty()) {
                        showAlert("No Items Selected \n Please Try Again");
                    } else {
                        showAlert("Items Purchased Successfully");
                    }
                }
            });
    }
}