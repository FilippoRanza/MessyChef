package com.example.messychef.utils;

import android.app.Activity;

import com.example.messychef.ShowIngredientListFragment;
import com.example.messychef.ShowProcessIngredientsStepFragment;
import com.example.messychef.ShowTakeIngredientStepFragment;
import com.example.messychef.ShowTimerStepFragment;
import com.example.messychef.recipe.RecipeRunner;

public class RecipeStepFragmentFactory {

    private final Activity owner;
    private final RecipeRunner runner;

    public RecipeStepFragmentFactory(Activity owner) {
        this.owner = owner;
        this.runner = RecipeRunner.getInstance();
    }


    public ShowIngredientListFragment showIngredientListFragmentFactory() {
        return new ShowIngredientListFragment(owner, runner.getIngredients());
    }

    public ShowTimerStepFragment showTimerStepFragmentFactory() {
        return new ShowTimerStepFragment();
    }


    public ShowProcessIngredientsStepFragment showProcessIngredientsStepFragmentFactory() {
        return new ShowProcessIngredientsStepFragment();
    }

    public ShowTakeIngredientStepFragment showTakeIngredientStepFragmentFactory() {
        return new ShowTakeIngredientStepFragment();
    }



}
