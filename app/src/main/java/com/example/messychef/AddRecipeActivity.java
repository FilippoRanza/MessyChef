package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.ActivityStarter;

public class AddRecipeActivity extends AppCompatActivity {

    private static final String RECIPE_ID = "Recipe";
    ActivityStarter starter = new ActivityStarter(this);

    RecipeFactory factory;
    TextField textField;
    CharSequence name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        if (savedInstanceState == null) {
            factory = RecipeFactory.getInstance();
            factory.initFactory();
            textField = TextField
                    .fromIds(this, R.id.input_recipe_name_layout, R.id.input_recipe_name_field)
                    .addUpdateListener((cs) -> name = cs);
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