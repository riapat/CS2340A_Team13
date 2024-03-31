package com.example.cs2340a_team13.views;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cs2340a_team13.DatabaseAccess;
import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.model.Ingredient;
import com.example.cs2340a_team13.model.Recipe;


import java.util.ArrayList;
import java.util.List;

public class MyAdapterRecipe extends BaseAdapter {

    //private Context mContext;
    private List<String> mRecipeList;
    private List<Ingredient> pantry;

    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    public MyAdapterRecipe(List<String> recipeList, List<Ingredient> pantryList) {
        super(0, recipeList);
        mRecipeList = recipeList;
        pantry = pantryList;
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false);
        }

        String currentRecipe = mRecipeList.get(position);
        TextView recipeName = listItem.findViewById(R.id.recipe_name);
        recipeName.setText(currentRecipe);

        // Check if all ingredients are enough
        boolean allIngredientsEnough = areIngredientsEnough(currentRecipe);

        // Set sub-item text based on ingredient availability
        TextView ingredientAvailability = listItem.findViewById(R.id.ingredient_availability);
        ingredientAvailability.setText(allIngredientsEnough ? "Enough" : "Not Enough");

        return listItem;
    }

    private boolean areIngredientsEnough(String recipeName) {
        // Retrieve the recipe from your data source based on the recipeName
        Recipe recipe = databaseAccess.getRecipeByName(recipeName); // Example method to get recipe by name from a database

        // If recipe is null, return false
        if (recipe == null) {
            return false;
        }

        // Iterate through the ingredients of the recipe
        for (Ingredient recipeIngredient : recipe.getRecipeIngredients()) {
            // Check if the ingredient is available in the user's pantry
            boolean foundInPantry = false;
            for (Ingredient pantryIngredient : pantry) {
                if (pantryIngredient.getIngredientName().equalsIgnoreCase(recipeIngredient.getIngredientName())) {
                    // If ingredient found in pantry, check if quantity is enough
                    if (pantryIngredient.getQuantity() >= recipeIngredient.getQuantity()) {
                        foundInPantry = true;
                        break; // Move to the next recipe ingredient
                    }
                }
            }
            // If ingredient not found in pantry or quantity not enough, return false
            if (!foundInPantry) {
                return false;
            }
        }
        // If all ingredients are available in sufficient quantity, return true
        return true;
    }

}
