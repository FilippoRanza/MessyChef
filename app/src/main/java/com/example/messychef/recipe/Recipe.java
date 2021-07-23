package com.example.messychef.recipe;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) &&
                Arrays.equals(ingredients, recipe.ingredients) &&
                Arrays.equals(steps, recipe.steps);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(ingredients);
        result = 31 * result + Arrays.hashCode(steps);
        return result;
    }
}
