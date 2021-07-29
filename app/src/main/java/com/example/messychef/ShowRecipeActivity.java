package com.example.messychef;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messychef.delete_dialog.DeleteDialog;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.storage_facility.CurrentRecipe;
import com.example.messychef.storage_facility.StoreData;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FieldInitializer;

import java.io.IOException;

public class ShowRecipeActivity extends AbstractMenuActivity {


    private Recipe recipe;
    private String recipeFile;

    private final CurrentRecipe currentRecipe;
    private final StoreData storeData;
    private final ActivityStarter starter;

    public ShowRecipeActivity() {
        starter = new ActivityStarter(this);
        storeData = new StoreData(this);
        currentRecipe = new CurrentRecipe(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);
        initRecipe();
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
            recipeFile = currentRecipe.getCurrentRecipeName();
            recipe = storeData.loadRecipe(recipeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTextField() {
        FieldInitializer.getInstance(this)
                .initTextField(R.id.recipe_name_text_view, recipe.getName())
                .initTextField(R.id.ingredient_count_text_view, recipe.getIngredients().length)
                .initTextField(R.id.step_count_text_view, recipe.getSteps().length);
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
                    storeData.deleteFile(recipeFile);
                    finish();
                })
                .start();
    }


}