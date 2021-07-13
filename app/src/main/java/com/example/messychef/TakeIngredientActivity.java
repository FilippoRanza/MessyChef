package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Bundle;

import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.FragmentInstaller;

public class TakeIngredientActivity extends AppCompatActivity {

    private TextField actionName;
    private CharSequence name;

    private FragmentInstaller installer;

    public TakeIngredientActivity() {
        installer = new FragmentInstaller(this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_ingredient);
        initNameField();

    }

    private void initNameField() {
        actionName = new TextField(this, R.string.take_ingredient_hint)
                .addUpdateListener((cs) -> name = cs);
        installer.installFragment(R.id.take_ingredients_action_name, actionName);
    }

}