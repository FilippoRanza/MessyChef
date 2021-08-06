package com.example.messychef.choose_complexity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import com.example.messychef.R;
import com.example.messychef.recipe.Recipe;

public class ChooseComplexityFragment extends Fragment {


    private int complexity;
    private RadioSelectionListener listener;

    private int startComplexity;

    public ChooseComplexityFragment() {
        this(Recipe.RECIPE_MEDIUM);
    }

    public ChooseComplexityFragment(int startComplexity) {
        this.startComplexity = startComplexity;
        this.complexity = startComplexity;
    }

    public ChooseComplexityFragment setRadioSelectionListener(RadioSelectionListener listener) {
        this.listener = listener;
        return this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_complexity, container, false);
        initRadioButtons(view);
        return view;
    }

    private void initRadioButtons(View view) {
        RadioComplexityTuple[] tuples = {
                new RadioComplexityTuple(R.id.button_recipe_easy, Recipe.RECIPE_SIMPLE),
                new RadioComplexityTuple(R.id.button_recipe_very_easy, Recipe.RECIPE_VERY_SIMPLE),
                new RadioComplexityTuple(R.id.button_recipe_medium, Recipe.RECIPE_MEDIUM),
                new RadioComplexityTuple(R.id.button_recipe_very_hard, Recipe.RECIPE_VERY_HARD),
                new RadioComplexityTuple(R.id.button_recipe_hard, Recipe.RECIPE_HARD),

        };
        for (RadioComplexityTuple tuple : tuples) {
            RadioButton button = view.findViewById(tuple.id);
            button.setOnClickListener((v) ->
            {
                complexity = tuple.value;
                if (listener != null)
                    listener.selectionListener(complexity);
            });

            if(tuple.value == startComplexity)
                button.toggle();
        }
    }

    private static class RadioComplexityTuple {
        final int id;
        final int value;

        RadioComplexityTuple(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }


    public int getRecipeComplexity() {
        return complexity;
    }

}