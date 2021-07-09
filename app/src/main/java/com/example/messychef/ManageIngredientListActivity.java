package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeFactory;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ManageIngredientListActivity extends AppCompatActivity {

    private ActivityStarter starter;
    private AdapterView<ArrayAdapter<String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ingredient_list);
        starter = new ActivityStarter(this);
        list = findViewById(R.id.manage_ingredient_list);
        updateIngredientList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateIngredientList();
    }

    public void startAddRecipe(View v) {
        starter.start(AddIngredientActivity.class);
    }

    private void updateIngredientList() {
        RecipeFactory factory = RecipeFactory.getInstance();
        ArrayList<String> list = factory.getIngredients()
                .stream()
                .map(Ingredient::getName)
                .collect(
                        Collectors.toCollection(ArrayList::new)
                );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_element, list);
        this.list.setAdapter(adapter);
    }


}