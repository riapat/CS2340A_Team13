package com.example.cs2340a_team13.views;
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


import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MyAdapterRecipe extends BaseAdapter {

    //private Context mContext;
    private List<String> mRecipeList;
    private List<Ingredient> pantry;

    private DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    private List<String> recipeDummy = List.of("amma", "dada", "cookie");
    private List<Ingredient> pantryDummy = List.of(new Ingredient("sugar", 10, 300), new Ingredient("flour", 5, 200), new Ingredient("butter", 2, 600));

    public MyAdapterRecipe(List<String> recipeList, List<Ingredient> pantryList) {
//        super(0, recipeList);
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
            listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
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

        AtomicBoolean allIngredientsEnough = new AtomicBoolean(false);
        // Retrieve the recipe from your data source based on the recipeName
        databaseAccess.getRecipeByName(recipeName, (queriedRecipe) -> {
            Recipe recipe =  queriedRecipe;

            if (recipe == null) {
                allIngredientsEnough.set(false);
                return;
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
                    allIngredientsEnough.set(false);
                    return;
                }
            }
            // If all ingredients are available in sufficient quantity, return true
            allIngredientsEnough.set(true);
            return;
        }); // Example method to get recipe by name from a database

        return allIngredientsEnough.get();
    }

}
