package com.example.messychef.recipe;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    private final String name;
    private final Ingredient[] ingredients;
    private final Step[] steps;

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
