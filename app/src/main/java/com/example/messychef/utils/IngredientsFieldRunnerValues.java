package com.example.messychef.utils;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.example.messychef.R;
import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.recipe.Step;

public class IngredientsFieldRunnerValues<T extends Step> {

    private final T step;
    private final ArrayAdapter<String> adapter;

    public IngredientsFieldRunnerValues(Activity owner) {
        RecipeRunner runner = RecipeRunner.getInstance();
        this.step = runner.getStep();
        this.adapter = new ArrayAdapter<>(owner, R.layout.list_element, runner.getIngredientsName());
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public T getStep() {
        return step;
    }
}
