package com.example.messychef;

import android.os.Bundle;
import android.view.View;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.RecipeFactory;

public class ModifyIngredientActivity extends AbstractManageIngredientActivity {

    private RecipeFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Override
    public void commit(View v) {
        if(validateInput()) {
            Ingredient ingredient = new Ingredient(name.toString(), amount);
            factory.commitIngredient(ingredient);
            finish();
        }
    }

    private void initialize() {
        factory = RecipeFactory.getInstance();
        Ingredient ingredient = factory.getModifyIngredient();
        name = ingredient.getName();
        amount = ingredient.getAmount();
        setValues();
    }


}
