package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;

public class AddRecipeActivity extends AppCompatActivity {

    ActivityStarter starter = new ActivityStarter(this);
    FragmentInstaller installer = new FragmentInstaller(this);
    RecipeFactory factory;
    TextField textField;
    CharSequence name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            setContentView(R.layout.activity_add_recipe);
            if (savedInstanceState == null) {
                factory = RecipeFactory.getInstance();
                factory.initFactory();
                textField = new TextField(this, R.string.add_recipe_text)
                        .addUpdateListener((cs) -> name = cs);

                installer.installFragment(R.id.input_recipe_name, textField);
            }
        }
    }



    public void manageIngredients(View v) {
        starter.start(ManageIngredientListActivity.class);
    }

    public void manageSteps(View v) {
        starter.start(ManageStepListActivity.class);
    }

    public void saveRecipe(View v) {
        if (!textField.isEmpty()) {
            factory.setName(name.toString());
            Recipe recipe = factory.getRecipe();
            finish();
        }
    }


}