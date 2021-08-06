package com.example.messychef;

import android.os.Bundle;
import android.view.View;

import com.example.messychef.delete_dialog.DeleteDialog;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.recipe.load_store.RecipeLoadStore;
import com.example.messychef.storage_facility.CurrentRecipe;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FieldInitializer;

import java.io.IOException;

public class ShowRecipeActivity extends AbstractMenuActivity {


    private Recipe recipe;

    private final CurrentRecipe currentRecipe;
    private final RecipeLoadStore storeData;
    private final ActivityStarter starter;

    public ShowRecipeActivity() {
        starter = new ActivityStarter(this);
        storeData = RecipeLoadStore.getInstance();
        currentRecipe = new CurrentRecipe(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecipe();
        setContentView(R.layout.activity_show_recipe);
        initTextField();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initRecipe();
        initTextField();
    }

    private void initRecipe() {
        try {
            int recipeID = currentRecipe.getCurrentRecipeName();
            storeData.startLoadRecipeById(recipeID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTextField() {
        recipe = storeData.commitLoadRecipeById();
        FieldInitializer.getInstance(this)
                .initTextField(R.id.recipe_name_text_view, recipe.getName())
                .initTextField(R.id.ingredient_count_text_view, recipe.getIngredients().length)
                .initTextField(R.id.step_count_text_view, recipe.getSteps().length)
                .initTextField(R.id.recipe_complexity_view, recipeComplexityMessage())
                .initTextField(R.id.recipe_duration, secondToTimeString());
    }

    private String secondToTimeString() {
        int seconds = recipe.getRecipeDuration();
        int minutes = seconds / 60;
        int s = seconds % 60;

        int h = minutes / 60;
        int m = minutes % 60;

        return String.format("%02d:%02d:%02d", h, m, s);
    }

    private String recipeComplexityMessage() {
        int id = 0;
        switch (recipe.getRecipeComplexity()) {
            case Recipe.RECIPE_HARD:
                id = R.string.hard;
                break;
            case Recipe.RECIPE_MEDIUM:
                id = R.string.medium;
                break;
            case Recipe.RECIPE_SIMPLE:
                id = R.string.easy;
                break;
            case Recipe.RECIPE_VERY_HARD:
                id = R.string.very_hard;
                break;
            case Recipe.RECIPE_VERY_SIMPLE:
                id = R.string.very_easy;
                break;
        }

        return getString(id);
    }


    public void startRecipe(View view) {
        RecipeRunner.getInstance().setRecipe(recipe);
        starter.start(ExecuteRecipeActivity.class);
    }

    public void modifyRecipe(View view) {
        RecipeFactory factory = RecipeFactory.getInstance();
        factory.initFactoryFromRecipe(recipe);
        starter.start(AddRecipeActivity.class);
    }

    public void deleteRecipe(View view) {
        new DeleteDialog<>(this, null)
                .setMessage(R.string.ingredient_delete_confirm_message)
                .setTitle(R.string.ingredient_delete_confirm_title)
                .setDeleteMessageAction((n) -> {
                    storeData.startDeleteRecipe(recipe);
                    finish();
                })
                .start();
    }


}