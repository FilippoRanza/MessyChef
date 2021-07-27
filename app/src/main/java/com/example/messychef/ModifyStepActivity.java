package com.example.messychef;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;
import com.example.messychef.utils.ActivityStarter;

public class ModifyStepActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runModifyStep();
        finish();
    }

    private void runModifyStep() {
        RecipeFactory factory = RecipeFactory.getInstance();
        Step s = factory.getModifyStep();
        Class<?> activity = null;
        if(s instanceof RecipeTimer) {
            activity = ModifyTimerStepActivity.class;
        }
        else if(s instanceof RecipeProcess) {
            activity = ModifyProcessStepActivity.class;
        }
        else if(s instanceof TakeIngredientStep) {
            activity = ModifyTakeIngredientActivity.class;
        }
        System.out.println("Ciao");
        new ActivityStarter(this).start(activity);
    }

}
