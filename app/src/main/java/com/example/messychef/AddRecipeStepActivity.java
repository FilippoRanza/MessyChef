package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.messychef.utils.ActivityStarter;

public class AddRecipeStepActivity extends AppCompatActivity {

    private final ActivityStarter starter;

    public AddRecipeStepActivity() {
        starter = new ActivityStarter(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recepie_step);
    }

    public void startTakeIngredientActivity(View view) {
        starter.start(TakeIngredientActivity.class);
    }


    public void startProcessStepActivity(View view) {
        starter.start(AddProcessStepActivity.class);
    }
}