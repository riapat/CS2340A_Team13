package com.example.cs2340a_team13;

import com.example.cs2340a_team13.model.Recipe;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlphabeticalSortingStrategy implements SortingStrategy {
    @Override
    public void sort(List<Recipe> recipes) {
        Collections.sort(recipes, Comparator.comparing(Recipe::getRecipeName));
    }
}