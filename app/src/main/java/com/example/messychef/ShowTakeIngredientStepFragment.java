package com.example.messychef;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.messychef.utils.FieldInitializer;
import com.example.messychef.utils.IngredientsFieldRunnerValues;


public class ShowTakeIngredientStepFragment extends AbstractShowStepFragment {


    private final IngredientsFieldRunnerValues<?> fieldRunnerValues;

    public ShowTakeIngredientStepFragment(Activity owner) {
        fieldRunnerValues = new IngredientsFieldRunnerValues<>(owner);
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
                .initTextField(R.id.take_ingredient_name_value, fieldRunnerValues.getStep().getName());
        ListView listView = v.findViewById(R.id.take_ingredient_list_view);
        listView.setAdapter(fieldRunnerValues.getAdapter());
    }

}