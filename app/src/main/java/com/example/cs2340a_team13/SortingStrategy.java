package com.example.cs2340a_team13;
import com.example.cs2340a_team13.model.Recipe;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// SortingStrategy Interface
public interface SortingStrategy {
    void sort(List<Recipe> recipes);
}
