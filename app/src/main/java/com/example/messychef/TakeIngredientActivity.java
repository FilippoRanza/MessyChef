package com.example.messychef;

import android.os.Bundle;
import android.view.View;

import com.example.messychef.checkbox_list_manager.CheckBoxListFragment;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.FragmentInstaller;
import com.example.messychef.utils.SelectedIndex;

import java.util.ArrayList;

public class TakeIngredientActivity extends AbstractMenuActivity {

    protected TextField actionName;
    protected CharSequence name;

    private final FragmentInstaller installer;
    private CheckBoxListFragment fragment;

    public TakeIngredientActivity() {
        installer = new FragmentInstaller(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_ingredient);
        initNameField();
        initCheckBoxList();
    }

    protected void initNameField() {
        actionName = new TextField(this, R.string.take_ingredient_hint)
                .addUpdateListener((cs) -> name = cs);
        installer.installFragment(R.id.take_ingredients_action_name, actionName);
    }

    private void initCheckBoxList() {
        RecipeFactory factory = RecipeFactory.getInstance();
        fragment = new CheckBoxListFragment(this,
                factory.streamAvailableIngredients());
        preSelectCheckBoxList();
        installer.installFragment(R.id.take_ingredients_select_ingredients, fragment);
    }

    protected void preSelectCheckBoxList() {
    }

    public void addTakeIngredientStep(View v) {
        if (!actionName.isEmpty()) {
            makeTakeIngredientStep();
            finish();
        }
    }

    protected void makeTakeIngredientStep() {
        RecipeFactory factory = RecipeFactory.getInstance();
        factory.addTakeIngredientStep(name.toString(), fragment.getSelected());
    }

    protected ArrayList<SelectedIndex> getSelected() {
        return fragment.getSelected();
    }

    protected void setSelected(int index) {
        fragment.setSelectedIndex(index);
    }

}