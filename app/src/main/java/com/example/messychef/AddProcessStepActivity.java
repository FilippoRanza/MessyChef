package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.messychef.checkbox_list_manager.CheckBoxListFragment;
import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.text_manager.TextField;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;
import com.example.messychef.utils.SelectedIndex;

import java.util.ArrayList;

public class AddProcessStepActivity extends AppCompatActivity {

    private final ActivityStarter starter;
    private final FragmentInstaller installer;

    private TextField nameField;
    private CharSequence name;

    private CheckBoxListFragment checkBoxList;

    private TextField descriptionField;
    private CharSequence description;

    public AddProcessStepActivity() {
        starter = new ActivityStarter(this);
        installer = new FragmentInstaller(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_process_step);
        initTextField();
        initIngredientList();
        initProcessDescription();
    }


    public void addProcessStep(View view) {
        if(validate()) {
            createProcessStep();
            finish();
        }
    }


    private void initTextField() {
        nameField = new TextField(this, R.string.process_name_placeholder)
                .addUpdateListener((cs) -> name = cs);
        installer.installFragment(R.id.process_name_field, nameField);
    }

    private void initIngredientList() {
        RecipeFactory factory = RecipeFactory.getInstance();
        checkBoxList = new CheckBoxListFragment(this, factory.streamTakenIngredients());
        installer.installFragment(R.id.process_ingredient_list, checkBoxList);
    }

    private void initProcessDescription() {
        descriptionField = new TextField(this, R.string.process_description_placeholder)
                .addUpdateListener((cs) -> description = cs);
        installer.installFragment(R.id.process_description_field, descriptionField);
    }


    private boolean validate() {
        // made in two steps in order to avoid short circuit evaluation
        boolean validName = !nameField.isEmpty();
        boolean validDescription = !descriptionField.isEmpty();

        return validName && validDescription;
    }

    private void createProcessStep() {
        ArrayList<SelectedIndex> selected = checkBoxList.getSelected();
        RecipeFactory.getInstance().addProcessStep(name.toString(), description.toString(), selected);
    }

}