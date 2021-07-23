package com.example.messychef;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.messychef.recipe.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


public class ShowIngredientListFragment extends AbstractShowStepFragment {


    private final Ingredient[] ingredients;
    private final Activity owner;

    public ShowIngredientListFragment(Activity owner, Ingredient[] ingredients) {
        this.ingredients = ingredients;
        this.owner = owner;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_ingredient_list, container, false);
        initIngredientList(view);
        return view;
    }

    private void initIngredientList(View v) {
        ArrayList<String> ingredientName = Arrays.stream(ingredients)
                .map(Ingredient::getName)
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayAdapter<String> ingredientArrayAdapter = new ArrayAdapter<>(owner, R.layout.list_element, ingredientName);
        ListView lv = v.findViewById(R.id.show_ingredient_list);
        lv.setAdapter(ingredientArrayAdapter);
    }


}