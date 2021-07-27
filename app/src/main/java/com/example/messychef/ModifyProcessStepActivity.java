package com.example.messychef;

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
    }

    @Override
    protected void initTextField() {
        super.initTextField();

        nameField.setText(process.getName());
        name = process.getName();
    }


    @Override
    protected void initProcessDescription() {
        super.initProcessDescription();

        descriptionField.setText(process.getDescription());
        description = process.getDescription();
    }
}
