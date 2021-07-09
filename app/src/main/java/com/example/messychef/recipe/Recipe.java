package com.example.messychef.recipe;

import java.util.ArrayList;

public class Recipe {

    private String name;
    private Ingredient[] ingredients;
    private Step[] steps;

    public Recipe(String name, Ingredient[] ingredients, Step[] steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }


    public String getName() {
        return name;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }
}
