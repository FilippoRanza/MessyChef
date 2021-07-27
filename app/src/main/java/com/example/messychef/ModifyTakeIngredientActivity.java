package com.example.messychef;

import android.view.View;
import android.widget.Button;

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
        enableDeleteButton();
    }

    @Override
    protected void preSelectCheckBoxList() {
        super.preSelectCheckBoxList();
        for(int i : step.getIngredientIndexList())
            setSelected(i);
    }

    @Override
    protected void makeTakeIngredientStep() {
        step.setName(name.toString());
        RecipeFactory.getInstance().updateSelected(getSelected());
        RecipeFactory.getInstance().commitStep();
    }


    private void enableDeleteButton() {
        Button button = findViewById(R.id.delete_step_button);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new DeleteStepListener(this));
    }

}
