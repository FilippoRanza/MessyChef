package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.messychef.list_manager.ListManagerFragment;
import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ManageIngredientListActivity extends AppCompatActivity {

    final private ActivityStarter starter;
    private ListManagerFragment list;
    final private FragmentInstaller installer;

    public ManageIngredientListActivity() {
        starter = new ActivityStarter(this);
        installer = new FragmentInstaller(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ingredient_list);
        initializeListView();
        updateIngredientList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateIngredientList();
    }

    public void startAddRecipe(View v) {
        starter.start(AddIngredientActivity.class);
    }

    private void updateIngredientList() {
        list.updateList(RecipeFactory.getInstance().streamIngredients().map(Ingredient::getName));
    }


    private void initializeListView() {
        list = new ListManagerFragment(this)
                .setEmptyListMessage(R.string.empty_ingredient_list_message)
                .addItemClickListener(this::modifyIngredient);
        installer.installFragment(R.id.ingredient_list_fragment, list);
    }

    private void modifyIngredient(int id) {
        RecipeFactory.getInstance().setModifyIngredientId(id);
        starter.start(ModifyIngredientActivity.class);
    }


}