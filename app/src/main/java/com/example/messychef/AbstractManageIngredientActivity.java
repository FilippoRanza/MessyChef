package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.GeneralUtils;

public abstract class AbstractManageIngredientActivity extends AppCompatActivity {


    TextField nameField;
    TextField amountField;

    CharSequence name;
    Integer amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        initializeNameField();
        initializeQuantityField();
    }

    public abstract void commit(View v);


    protected boolean validateInput() {
        boolean output = true;
        if (nameField.isEmpty()) {
            output = false;
        }
        if (amountField.isEmpty()) {
            output = false;
        }

        return output;
    }


    private void initializeNameField() {
        nameField = TextField.fromIds(this, R.id.ingredient_name_layout, R.id.ingredient_name_field)
                .addUpdateListener((s) -> name = s);
    }

    private void initializeQuantityField() {
        amountField = TextField.fromIds(this, R.id.ingredient_quantity_layout, R.id.ingredient_quantity_field)
                .addUpdateListener((s) -> amount = GeneralUtils.parseInteger(s));
    }

}