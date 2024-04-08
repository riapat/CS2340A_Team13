package com.example.cs2340a_team13.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.model.Ingredient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShoppingListScreen extends AppCompatActivity {
    private List<CheckBox> shoppingListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_screen);

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
    //input shopping screen here + place text header

    private void initializeCart(List<CheckBox> shoppingListButtons, List<Ingredient> shoppingCart){
        shoppingListButtons = new LinkedList<CheckBox>();
        for (Ingredient cartItem:shoppingList){
            CheckBox item = new CheckBox(this);
            String name = cartItem.getIngredientName() + "\n QTY: " + cartItem.getQuantity();
            item.setText(name);
            item.setChecked(false);
            shoppingListButtons.add(item);
        }
    }

    private List<Ingredient> getSelectedIngredients(List<CheckBox> shoppingListButtons,
                                                    List<Ingredient> shoppingCart){
        List<Ingredient> selectedItems = new ArrayList<Ingredient>();
        for (CheckBox item:shoppingListButtons) {
            if(item.isChecked()){
                String[] itemInfo = item.getText().toString().split("\\R");
                for (Ingredient shoppingCartItem: shoppingCart) {
                    if (shoppingCartItem.getIngredientName().equals(itemInfo[0]){
                        selectedItems.add(shoppingCartItem);
                        break;
                    }
                }
            }
        }
        return selectedItems;
    }
}