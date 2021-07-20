package com.example.messychef;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.messychef.recipe.TakeIngredientStep;
import com.example.messychef.utils.FieldInitializer;


public class ShowTakeIngredientStepFragment extends AbstractShowStepFragment {


    private final TakeIngredientStep step;

    public ShowTakeIngredientStepFragment(TakeIngredientStep step) {
        this.step = step;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_show_take_ingredient_step, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View v) {
        FieldInitializer.getInstance(v)
                .initTextField(R.id.take_ingredient_name_value, step.getName());
    }

}