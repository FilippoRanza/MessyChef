package com.example.messychef;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;
import com.example.messychef.timer_service.TimerServiceCache;
import com.example.messychef.utils.AppFocus;
import com.example.messychef.utils.FieldInitializer;
import com.example.messychef.utils.FragmentInstaller;
import com.example.messychef.utils.RecipeStepFragmentFactory;

public class ExecuteRecipeActivity extends AbstractMenuActivity {

    public static final String AUTO_NEXT = "AUTO_NEXT";

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
        runAction();
        initTextField();
        update();
        AppFocus.getInstance().setActivity(this);
    }

    private void runAction() {
        Intent intent = getIntent();
        if (intent.getAction() != null) {
            TimerServiceCache cache = TimerServiceCache.getInstance();
            cache.getTimerService().runSnooze();
            cache.getTimerService().stopSelf();
        }
    }

    private void initTextField() {
        FieldInitializer.getInstance(this)
                .initTextField(R.id.recipe_name_text_view, runner.getRecipeName());

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppFocus.getInstance().setFocus(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppFocus.getInstance().setFocus(false);
        AppFocus.getInstance().setActivity(this);
    }

    public void showIngredientList(View view) {
        switch (status) {
            case Step:
                installShowIngredientList();
                setIngredientListButtonText(R.string.ingredient_list_button_back);
                status = ShowStatus.Ingredients;
                break;
            case Ingredients:
                installFragment();
                setIngredientListButtonText(R.string.show_ingredients_button);
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
        resetShowIngredientListButton();
        step = runner.getStep();
        updateButtons();
        updateStepNameView();
        installFragment();
    }

    private void setIngredientListButtonText(int id) {
        Button button = findViewById(R.id.show_ingredient_list_button);
        button.setText(id);
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
        int visibility = cond ? View.VISIBLE : View.INVISIBLE;
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
        if (step instanceof RecipeTimer) {
            return R.string.timer_step;
        }
        return R.string.last_step;
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
        if (step instanceof RecipeTimer) {
            return fragmentFactory.showTimerStepFragmentFactory();
        }
        return fragmentFactory.showLastStepFragmentFactory();
    }


    private void updateFragment(AbstractShowStepFragment fragment) {
        installer.removeFragment(R.id.recipe_step_fragment)
                .installFragment(R.id.recipe_step_fragment, fragment);
    }


    private void resetShowIngredientListButton() {
        status = ShowStatus.Step;
        setIngredientListButtonText(R.string.show_ingredients_button);
    }


}





















