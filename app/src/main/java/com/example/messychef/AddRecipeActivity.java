package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AddRecipeActivity extends AppCompatActivity {

    ActivityStarter starter = new ActivityStarter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
    }


    public void addIngredient(View v) {
        starter.start(AddIngredientActivity.class);
    }

    public void addStep(View v) {
        starter.start(AddRecipeStepActivity.class);
    }

    public void saveRecipe(View v) {
        finish();
    }


}