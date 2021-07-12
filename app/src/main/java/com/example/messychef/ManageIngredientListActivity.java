package com.example.messychef;

import android.view.View;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.RecipeFactory;

public class ManageIngredientListActivity extends AbstractManageItemListActivity {
    @Override
    public void startAddActivity(View v) {
        starter.start(AddIngredientActivity.class);

    }

    @Override
    protected void initializeView() {
        list.setEmptyListMessage(R.string.empty_ingredient_list_message)
                .addItemClickListener(this::modifyIngredient);
        installer.installFragment(R.id.item_list_fragment, list);
    }

    @Override
    protected void upgradeItemList() {
        list.updateList(RecipeFactory.getInstance().streamIngredients().map(Ingredient::getName));
    }


    private void modifyIngredient(int id) {
        RecipeFactory.getInstance().setModifyIngredientId(id);
        starter.start(ModifyIngredientActivity.class);
    }


}
