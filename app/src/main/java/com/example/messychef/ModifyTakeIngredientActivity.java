package com.example.messychef;

import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.recipe.TakeIngredientStep;

public class ModifyTakeIngredientActivity extends TakeIngredientActivity {

    private final TakeIngredientStep step;

    public ModifyTakeIngredientActivity() {
        step = (TakeIngredientStep) RecipeFactory.getInstance().getModifyStep();
    }



    @Override
    protected void initNameField() {
        super.initNameField();
        name = step.getName();
        actionName.setText(step.getName());

    }
}
