package com.example.messychef.recipe;

import java.util.Arrays;
import java.util.Objects;

public class TakeIngredientStep extends Step implements IngredientList {

    private int[] ingredients;

    private String name;

    public TakeIngredientStep(String name, int[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(int[] ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TakeIngredientStep that = (TakeIngredientStep) o;
        return Arrays.equals(ingredients, that.ingredients) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(ingredients);
        return result;
    }

    @Override
    public String toString() {
        return "TakeIngredientStep{" +
                "ingredients=" + Arrays.toString(ingredients) +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int[] getIngredientIndexList() {
        return ingredients;
    }
}
