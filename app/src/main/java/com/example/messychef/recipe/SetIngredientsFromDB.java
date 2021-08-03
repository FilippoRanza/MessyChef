package com.example.messychef.recipe;

public interface SetIngredientsFromDB {
    void setIngredientList(int[] list);

    int[] getIngredients();

    int getType();

    int getId();
}
