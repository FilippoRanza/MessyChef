package com.example.messychef;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.messychef.utils.ActivityStarter;

public class LastStepFragment extends AbstractShowStepFragment {

    private ActivityStarter starter;

    public LastStepFragment(Activity owner) {
        starter = new ActivityStarter(owner);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last_step, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initButton(view, R.id.back_to_recipe_list, this::backToRecipeList);
        initButton(view, R.id.back_to_recipe_menu, this::backToRecipeMenu);
    }

    private void initButton(View v, int id, View.OnClickListener listener){
        Button button = v.findViewById(id);
        button.setOnClickListener(listener);
    }

    private void backToRecipeList(View v) {
        starter.setActivity(RecipeListActivity.class)
                .setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .start();
    }


    private void backToRecipeMenu(View v) {
        starter.setActivity(ShowRecipeActivity.class)
                .setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .start();
    }


}