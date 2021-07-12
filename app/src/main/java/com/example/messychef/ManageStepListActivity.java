package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.recipe.Step;

public class ManageStepListActivity extends AbstractManageItemListActivity {

    @Override
    public void startAddActivity(View v) {
        starter.start(AddRecipeStepActivity.class);
    }

    @Override
    protected void initializeView() {
        list.setEmptyListMessage(R.string.empty_step_list_message);
        installer.installFragment(R.id.item_list_fragment, list);
    }

    @Override
    protected void upgradeItemList() {
        list.updateList(RecipeFactory.getInstance().streamSteps().map(Step::getName));

    }
}