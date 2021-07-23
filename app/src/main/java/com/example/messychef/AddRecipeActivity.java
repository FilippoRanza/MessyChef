package com.example.messychef;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.storage_facility.CurrentRecipe;
import com.example.messychef.storage_facility.FileInfo;
import com.example.messychef.storage_facility.StoreData;
import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;

import java.io.IOException;

public class AddRecipeActivity extends AppCompatActivity {

    private final ActivityStarter starter = new ActivityStarter(this);
    private final FragmentInstaller installer = new FragmentInstaller(this);
    private RecipeFactory factory;
    private TextField textField;
    private CharSequence name;

    private final CurrentRecipe currentRecipe;
    private final StoreData storeData;

    public AddRecipeActivity() {
        storeData = new StoreData(this);
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
        Recipe recipe = factory.getRecipe();
        String fileName = currentRecipe.getCurrentRecipeName();
        if (fileName != null)
            updateRecipe(recipe, fileName);
        else
            storeData.saveRecipe(recipe);

    }

    private void updateRecipe(Recipe recipe, String currFile) throws IOException {
        FileInfo info = storeData.updateRecipe(recipe, currFile);
        String newFile = info.getFileName();
        currentRecipe.setCurrentRecipeName(newFile);
    }
}