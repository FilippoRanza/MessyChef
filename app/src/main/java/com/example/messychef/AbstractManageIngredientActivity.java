package com.example.messychef;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.FragmentInstaller;
import com.example.messychef.utils.GeneralUtils;

public abstract class AbstractManageIngredientActivity extends AbstractMenuActivity {


    TextField nameField;
    TextField amountField;

    CharSequence name;
    Double quantity;

    FragmentInstaller installer;

    AbstractManageIngredientActivity() {
        installer = new FragmentInstaller(this);
    }


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


    protected void setValues() {
        nameField.setText(name);
        amountField.setText("" + quantity);
    }

    private void initializeNameField() {
        nameField = new TextField(this, R.string.ingredient_name_field).addUpdateListener((s) -> name = s);
        installer.installFragment(R.id.ingredient_name_field, nameField);
    }

    private void initializeQuantityField() {
        amountField = new TextField(this, R.string.ingredient_quantity_hint)
                .addUpdateListener((s) -> quantity = GeneralUtils.parseDouble(s))
                .setInputType(TextField.NUMBER_INPUT);
        installer.installFragment(R.id.ingredient_quantity_field, amountField);
    }

}