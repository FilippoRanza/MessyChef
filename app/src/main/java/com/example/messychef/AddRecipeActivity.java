package com.example.messychef;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.recipe.load_store.RecipeLoadStore;
import com.example.messychef.storage_facility.CurrentRecipe;
import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;

import java.io.IOException;

public class AddRecipeActivity extends AbstractMenuActivity {

    private final ActivityStarter starter = new ActivityStarter(this);
    private final FragmentInstaller installer = new FragmentInstaller(this);
    private RecipeFactory factory;
    private TextField textField;
    private CharSequence name;

    private final CurrentRecipe currentRecipe;
    private final RecipeLoadStore storeData;

    public AddRecipeActivity() {
        storeData = RecipeLoadStore.getInstance();
        currentRecipe = new CurrentRecipe(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_add_recipe);
            factory = RecipeFactory.getInstance();
            textField = new TextField(this, R.string.add_recipe_text)
                    .addUpdateListener((cs) -> name = cs);
            if (factory.shouldInitialize())
                factory.initFactory();
            else
                initNameField();

            installer.installFragment(R.id.input_recipe_name, textField);
        }
    }

    private void initNameField() {
        textField.setText(factory.getName());
        name = factory.getName();
    }


    public void manageIngredients(View v) {
        starter.start(ManageIngredientListActivity.class);
    }

    public void manageSteps(View v) {
        starter.start(ManageStepListActivity.class);
    }

    public void saveRecipe(View v) {

        if (!textField.isEmpty()) {
            save();
            finish();
        }
    }


    private void save() {
        try {
            runSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void runSave() throws IOException {
        factory.setName(name.toString());
        factory.setComplexity(getRecipeComplexity());
        Recipe recipe = factory.getRecipe();
        Integer recipeID = currentRecipe.getCurrentRecipeName();
        if (recipeID != null)
            updateRecipe(recipe, recipeID);
        else
            storeData.startSaveRecipe(recipe);

    }

    private void updateRecipe(Recipe recipe, int recipeId) throws IOException {
        recipe.setRecipeID(recipeId);
        recipe.updateComponentID();
        storeData.startUpdateRecipe(recipe);
        currentRecipe.setCurrentRecipeName(recipeId);
    }

    private int getRecipeComplexity() {
        RadioGroup group = findViewById(R.id.recipe_complexity_group);
        int id = group.getCheckedRadioButtonId();
        int output = 0;
        switch (id) {
            case R.id.button_recipe_very_easy:
                output = Recipe.RECIPE_VERY_SIMPLE;
                break;

            case R.id.button_recipe_easy:
                output = Recipe.RECIPE_SIMPLE;
                break;
            case R.id.button_recipe_medium:
                output = Recipe.RECIPE_MEDIUM;
                break;
            case R.id.button_recipe_hard:
                output = Recipe.RECIPE_HARD;
                break;
            case R.id.button_recipe_very_hard:
                output = Recipe.RECIPE_VERY_HARD;
                break;
        }
        return output;
    }
}