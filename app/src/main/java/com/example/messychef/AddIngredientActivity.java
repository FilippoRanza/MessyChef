package com.example.messychef;

import android.view.View;

public class AddIngredientActivity extends AbstractManageIngredientActivity {


    @Override
    public void commit(View v) {
        if(validateInput()) {
            finish();
        }
    }
}
