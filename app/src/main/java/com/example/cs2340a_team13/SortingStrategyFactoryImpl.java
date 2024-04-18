package com.example.cs2340a_team13;

public class SortingStrategyFactoryImpl implements SortingStrategyFactory {
    @Override
    public SortingStrategy createSortingStrategy(String type) {
        if (type.equalsIgnoreCase("alphabetical")) {
            return new AlphabeticalSortingStrategy();
        } else if (type.equalsIgnoreCase("numberofingredients")) {
            return new NumberOfIngredientsSortingStrategy();
        }
        throw new IllegalArgumentException("Unknown sorting type: " + type);
    }
}
