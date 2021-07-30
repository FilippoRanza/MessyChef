package com.example.messychef;

import android.os.Bundle;
import android.view.View;

import com.example.messychef.delete_dialog.DeleteDialog;
import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.utils.FragmentInstaller;

public class ModifyIngredientActivity extends AbstractManageIngredientActivity {

    private RecipeFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFields();
        initializeDelete();
    }

    @Override
    public void commit(View v) {
        if (validateInput()) {
            Ingredient ingredient = new Ingredient(name.toString(), quantity, unit.toString());
            factory.commitIngredient(ingredient);
            finish();
        }
    }

    private void initializeFields() {
        factory = RecipeFactory.getInstance();
        Ingredient ingredient = factory.getModifyIngredient();
        name = ingredient.getName();
        quantity = ingredient.getQuantity();
        setValues();
    }

    private void initializeDelete() {
        FragmentInstaller installer = new FragmentInstaller(this);
        DeleteItemFragment fragment = new DeleteItemFragment(this::deleteIngredient);
        installer.installFragment(R.id.ingredient_plugin_fragment, fragment);
    }


    private void deleteIngredient() {
        new DeleteDialog<>(this, null)
                .setTitle(R.string.ingredient_delete_confirm_title)
                .setMessage(R.string.ingredient_delete_confirm_message)
                .setDeleteMessageAction((o) -> {
                    RecipeFactory.getInstance().deleteModifyIngredient();
                    finish();
                })
                .start();
    }

}
