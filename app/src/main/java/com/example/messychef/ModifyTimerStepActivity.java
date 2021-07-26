package com.example.messychef;

import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.recipe.RecipeTimer;

public class ModifyTimerStepActivity extends AddTimerActivity {

    private final RecipeTimer timer;

    public ModifyTimerStepActivity() {
        timer = (RecipeTimer) RecipeFactory.getInstance().getModifyStep();
    }

    @Override
    protected void initNameField() {
        super.initNameField();
        nameField.setText(timer.getName());
        name = timer.getName();
    }

    @Override
    protected void initTimePicker() {
        super.initTimePicker();
        int time = timer.getGlobalTime();
        int h = time / 3600;
        time %= 3600;
        int m = time / 60;
        int s = time % 60;
        picker.setHour(h);
        picker.setMinute(m);
        picker.setSecond(s);
    }

    @Override
    protected void createTimerStep() {
        RecipeFactory factory = RecipeFactory.getInstance();
        timer.setName(name.toString());
        timer.setGlobalTime(getSeconds());
        Integer stepTimer = controller.getSelectedTime();
        if(stepTimer != null)
            stepTimer *= 60;
        timer.setStepTime(stepTimer);
    }
}
