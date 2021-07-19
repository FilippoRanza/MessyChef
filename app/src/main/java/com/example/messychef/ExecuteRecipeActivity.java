package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;
import com.example.messychef.utils.FieldInitializer;

public class ExecuteRecipeActivity extends AppCompatActivity {

    private RecipeRunner runner;
    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_recipe);
        runner = RecipeRunner.getInstance();
        initTextField();

        update();
    }

    private void initTextField() {
        FieldInitializer.getInstance(this)
                .initTextField(R.id.recipe_name_text_view, runner.getRecipeName());

    }

    public void showIngredientList(View view) {
    }

    public void showPervStep(View view) {
        runner.prevStep();
        update();
    }

    public void showNextStep(View view) {
        runner.nextStep();
        update();
    }


    private void update() {
        step = runner.getStep();
        updateButtons();
        updateStepNameView();
    }

    private void updateButtons() {
        enableButton(runner.hasNext(), R.id.next_step_button);
        enableButton(runner.hasPrev(), R.id.prev_step_button);

    }

    private void enableButton(boolean enable, int id) {
        Button button = findViewById(id);
        button.setEnabled(enable);
    }


    private void updateStepNameView() {
        int name = getStepName();
        TextView textView = findViewById(R.id.step_type_text_view);
        textView.setText(name);
    }

    private int getStepName() {
        if (step == null) {
            return R.string.showIngredients_step;
        }

        if (step instanceof TakeIngredientStep) {
            return R.string.take_ingredient_step_name;
        }
        if (step instanceof RecipeProcess) {
            return R.string.process_ingredient_step_name;
        }
        return R.string.timer_step;

    }


}





















