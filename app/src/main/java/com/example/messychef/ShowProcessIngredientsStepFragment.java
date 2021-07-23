package com.example.messychef;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.utils.FieldInitializer;
import com.example.messychef.utils.IngredientsFieldRunnerValues;


public class ShowProcessIngredientsStepFragment extends AbstractShowStepFragment {

    private IngredientsFieldRunnerValues<RecipeProcess> ingredientsFieldRunnerValues;

    public ShowProcessIngredientsStepFragment(Activity owner) {
        ingredientsFieldRunnerValues = new IngredientsFieldRunnerValues<>(owner);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_process_ingredients_step, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        initTextViews(v);
    }

    private void initTextViews(View v) {
        RecipeProcess rp = ingredientsFieldRunnerValues.getStep();
        FieldInitializer.getInstance(v)
                .initTextField(R.id.process_name_text_view, rp.getName())
                .initTextField(R.id.process_description_view, rp.getDescription());
    }


}