package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.View;

public class AddIngredientActivity extends AppCompatActivity {

    private ManageIngredientFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);


        if(savedInstanceState == null) {
            fragment = addIngredientFragment();
        }


    }

    public void addIngredient(View v) {
        System.out.println(fragment.getName());
        System.out.println(fragment.getQuantity());
        finish();
    }

    private ManageIngredientFragment addIngredientFragment() {

        ManageIngredientFragment fragment = ManageIngredientFragment.createIngredientInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.insert_new_ingredient, fragment)
                .commit();

        return fragment;
    }

}