package com.example.messychef.recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class RecipeProcess extends Step implements Serializable {

    private final String description;
    private final int[] ingredients;

    private final String name;

    public RecipeProcess(String name, String description, int[] ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }



    @Override
    public String getName() {
        return name;
    }

    public int[] getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeProcess that = (RecipeProcess) o;
        return Objects.equals(description, that.description) &&
                Arrays.equals(ingredients, that.ingredients) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(description, name);
        result = 31 * result + Arrays.hashCode(ingredients);
        return result;
    }

    @Override
    public String toString() {
        return "RecipeProcess{" +
                "description='" + description + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", name='" + name + '\'' +
                '}';
    }
}
