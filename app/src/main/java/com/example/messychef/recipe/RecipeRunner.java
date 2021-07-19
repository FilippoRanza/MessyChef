package com.example.messychef.recipe;

import android.content.Intent;

import com.example.messychef.storage_facility.StoreData;

public class RecipeRunner {

    private static final int INGREDIENT_STEP = -1;
    private static RecipeRunner instance;

    private Recipe recipe;
    private int step;

    private RecipeRunner() {
    }

    public static RecipeRunner getInstance() {
        if (instance == null) {
            instance = new RecipeRunner();
        }
        return instance;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        step = INGREDIENT_STEP;
    }

    public boolean hasNext() {
        return step < (recipe.getSteps().length - 1);
    }

    public boolean hasPrev() {
        return step > 0;
    }

    public void nextStep() {
        this.step++;
    }

    public void prevStep() {
        step--;
        if (step < 0)
            step = 0;
    }


    public Step getStep() {
        return (step >= 0) ?
                recipe.getSteps()[step] : null;
    }

    public Ingredient[] getIngredients() {
        return recipe.getIngredients();
    }

    public void reset() {
        this.recipe = null;
    }

    public String getRecipeName() {
        return recipe.getName();
    }

}
