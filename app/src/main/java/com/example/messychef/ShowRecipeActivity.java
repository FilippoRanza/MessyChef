package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.messychef.delete_dialog.DeleteDialog;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.storage_facility.FileInfo;
import com.example.messychef.storage_facility.StoreData;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FieldInitializer;
import com.example.messychef.utils.FragmentInstaller;

import java.io.IOException;

public class ShowRecipeActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA_ID = "Recipe";
    private Recipe recipe;
    private FileInfo info;
    private final StoreData storeData;

    private final ActivityStarter starter;

    public ShowRecipeActivity() {
        starter = new ActivityStarter(this);
        storeData = new StoreData(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);
        initRecipe();
        initTextField();
    }


    private void initRecipe() {
        Intent intent = getIntent();
        info = (FileInfo) intent.getSerializableExtra(RECIPE_EXTRA_ID);
        try {
            recipe = storeData.loadRecipe(info);
        } catch (IOException | ClassNotFoundException e) {
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
    }

    public void deleteRecipe(View view) {
        new DeleteDialog<>(this,  null)
                .setMessage(R.string.ingredient_delete_confirm_message)
                .setTitle(R.string.ingredient_delete_confirm_title)
                .setDeleteMessageAction((n) -> {
                    storeData.deleteFile(info);
                    finish();
                })
                .start();
    }


}