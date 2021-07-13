package com.example.messychef.recipe;

import java.util.ArrayList;

public class TakeIngredientStep extends Step {

    private ArrayList<Ingredient> ingredients;

    private String name;

    public TakeIngredientStep(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    @Override
    public String getName() {
        return name;
    }
}
