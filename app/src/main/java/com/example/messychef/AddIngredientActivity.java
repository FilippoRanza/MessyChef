package com.example.messychef;

import android.view.View;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.RecipeFactory;

public class AddIngredientActivity extends AbstractManageIngredientActivity {


    @Override
    public void commit(View v) {
        if (validateInput()) {
            sendResult();
            finish();
        }
    }

    private void sendResult() {
        RecipeFactory.getInstance().addIngredient(makeIngredient());
    }


    private Ingredient makeIngredient() {
        return new Ingredient(name.toString(), quantity, unit.toString());
    }


}
