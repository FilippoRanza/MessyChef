package com.example.messychef;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.messychef.recipe.load_store.RecipeLoadStore;
import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.FragmentInstaller;
import com.example.messychef.utils.GeneralUtils;

public abstract class AbstractManageIngredientActivity extends AbstractMenuActivity {


    private TextField nameField;
    private TextField amountField;

    CharSequence name;
    Double quantity;

    private final FragmentInstaller installer;

    CharSequence unit;

    private final RecipeLoadStore loadStore;


    AbstractManageIngredientActivity() {
        installer = new FragmentInstaller(this);
        loadStore = RecipeLoadStore.getInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        initializeNameField();
        initializeQuantityField();
        initSpinner();
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
        nameField = new TextField(this, R.string.ingredient_name_field)
                .addUpdateListener(this::inputListener)
                .addUpdateAutocomplete(loadStore::commitSearchIngredient)
                .addFocusGetCallback(loadStore::startCacheDatabase)
                .addFocusLostCallback(loadStore::stopCacheDatabase);
        installer.installFragment(R.id.ingredient_name_field, nameField);
    }

    private void inputListener(CharSequence cs) {
        name = cs;
        loadStore.startSearchIngredient(cs.toString());
    }

    private void initializeQuantityField() {
        amountField = new TextField(this, R.string.ingredient_quantity_hint)
                .addUpdateListener((s) -> quantity = GeneralUtils.parseDouble(s))
                .setInputType(TextField.NUMBER_INPUT);
        installer.installFragment(R.id.ingredient_quantity_field, amountField);
    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.unit_spinner);
        String[] units = {"Kg", "g", "l", "ml", "qt."};
        unit = units[0];
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_element, units);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unit = units[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}