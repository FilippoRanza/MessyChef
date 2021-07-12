package com.example.messychef.recipe;

import java.util.ArrayList;

public class TakeIngredientStep extends Step {

    private ArrayList<Ingredient> ingredients;

    private String name;


    @Override
    public String getName() {
        return name;
    }
}
