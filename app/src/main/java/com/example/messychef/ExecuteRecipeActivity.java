package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.utils.FieldInitializer;

public class ExecuteRecipeActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_recipe);
        
        initTextField();
        
        
    }

    private void initTextField() {
        FieldInitializer.getInstance(this)
                .initTextField(R.id.recipe_name_text_view, recipe.getName());
    }
}