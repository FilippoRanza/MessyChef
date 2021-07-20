package com.example.messychef;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.recipe.TakeIngredientStep;
import com.example.messychef.utils.FieldInitializer;


public class ShowTakeIngredientStepFragment extends AbstractShowStepFragment {


    private final TakeIngredientStep step;
    private final ArrayAdapter<String> adapter;

    public ShowTakeIngredientStepFragment(Activity owner) {
        RecipeRunner runner = RecipeRunner.getInstance();
        this.step = runner.getStep();
        adapter = new ArrayAdapter<>(owner, R.layout.list_element, runner.getIngredientsName());
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
        ListView listView = v.findViewById(R.id.take_ingredient_list_view);
        listView.setAdapter(adapter);
    }

}