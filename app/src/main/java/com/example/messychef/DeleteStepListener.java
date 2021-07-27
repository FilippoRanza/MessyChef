package com.example.messychef;

import android.app.Activity;
import android.view.View;

import com.example.messychef.delete_dialog.DeleteDialog;
import com.example.messychef.recipe.RecipeFactory;

public class DeleteStepListener implements View.OnClickListener {

    private final Activity owner;

    public DeleteStepListener(Activity owner) {
        this.owner = owner;
    }

    @Override
    public void onClick(View view) {
        new DeleteDialog<>(owner, null)
                .setDeleteMessageAction((v) -> {
                    RecipeFactory.getInstance().deleteModifyStep();
                    owner.finish();
                })
                .setTitle(R.string.ingredient_delete_confirm_title)
                .setMessage(R.string.ingredient_delete_confirm_message)
                .start();
    }
}
