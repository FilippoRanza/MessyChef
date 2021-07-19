package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;
import com.example.messychef.utils.FieldInitializer;
import com.example.messychef.utils.FragmentInstaller;
import com.example.messychef.utils.RecipeStepFragmentFactory;

public class ExecuteRecipeActivity extends AppCompatActivity {

    private final RecipeRunner runner;
    private Step step;
    private final FragmentInstaller installer;
    private final RecipeStepFragmentFactory fragmentFactory;

    private enum ShowStatus {
        Step,
        Ingredients
    }

    private ShowStatus status;


    public ExecuteRecipeActivity() {
        installer = new FragmentInstaller(this);
        runner = RecipeRunner.getInstance();
        fragmentFactory = new RecipeStepFragmentFactory(this);
        status = ShowStatus.Step;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_recipe);
        initTextField();
        update();
    }

    private void initTextField() {
        FieldInitializer.getInstance(this)
                .initTextField(R.id.recipe_name_text_view, runner.getRecipeName());

    }

    public void showIngredientList(View view) {
        switch (status) {
            case Step:
                installShowIngredientList();
                status = ShowStatus.Ingredients;
                break;
            case Ingredients:
                installFragment();
                status = ShowStatus.Step;
                break;
        }
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
        installFragment();
    }

    private void installShowIngredientList() {
        ShowIngredientListFragment fragment = fragmentFactory.showIngredientListFragmentFactory();
        updateFragment(fragment);
    }

    private void updateButtons() {
        enableButton(runner.hasNext(), R.id.next_step_button);
        enableButton(runner.hasPrev(), R.id.prev_step_button);
        showIngredientListButton(step != null);
    }

    private void enableButton(boolean enable, int id) {
        Button button = findViewById(id);
        button.setEnabled(enable);
    }

    private void showIngredientListButton(boolean cond) {
        Button button = findViewById(R.id.show_ingredient_list_button);
        int visibility = cond ? View.VISIBLE: View.INVISIBLE;
        button.setVisibility(visibility);
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


    private void installFragment() {
        updateFragment(getStepFragment());
    }

    private AbstractShowStepFragment getStepFragment() {
        if (step == null) {
            return fragmentFactory.showIngredientListFragmentFactory();
        }
        if (step instanceof TakeIngredientStep) {
            return fragmentFactory.showTakeIngredientStepFragmentFactory();
        }
        if (step instanceof RecipeProcess) {
            return fragmentFactory.showProcessIngredientsStepFragmentFactory();
        }
        return fragmentFactory.showTimerStepFragmentFactory();
    }



    private void updateFragment(AbstractShowStepFragment fragment) {
        installer.removeFragment(R.id.recipe_step_fragment)
                .installFragment(R.id.recipe_step_fragment, fragment);
    }




}





















