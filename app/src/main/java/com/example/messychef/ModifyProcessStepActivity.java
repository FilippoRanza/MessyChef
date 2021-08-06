package com.example.messychef;

import android.view.View;
import android.widget.Button;

import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.recipe.RecipeProcess;

public class ModifyProcessStepActivity extends AddProcessStepActivity {

    private final RecipeProcess process;

    public ModifyProcessStepActivity() {
        process = (RecipeProcess) RecipeFactory.getInstance().getModifyStep();
        assert process != null;
    }


    @Override
    protected void createProcessStep() {
        process.setName(name.toString());
        process.setDescription(description.toString());
        RecipeFactory.getInstance().updateSelected(getSelected());
        RecipeFactory.getInstance().commitStep();
    }

    @Override
    protected void initTextField() {
        super.initTextField();

        nameField.setText(process.getName());
        name = process.getName();
        enableDeleteButton();
    }

    @Override
    protected void initTimePicker() {
        super.initTimePicker();
        setCurrentTime(process.getDuration());
    }

    @Override
    protected void preSelectCheckBoxList() {
        super.preSelectCheckBoxList();
        for (int i : process.getIngredientIndexList())
            setSelected(i);
    }

    @Override
    protected void initProcessDescription() {
        super.initProcessDescription();

        descriptionField.setText(process.getDescription());
        description = process.getDescription();
    }

    private void enableDeleteButton() {
        Button button = findViewById(R.id.delete_step_button);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new DeleteStepListener(this));
    }

}
