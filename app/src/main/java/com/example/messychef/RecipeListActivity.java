package com.example.messychef;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.messychef.list_manager.ListManagerFragment;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.load_store.RecipeLoadStore;
import com.example.messychef.storage_facility.CurrentRecipe;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;

import java.io.IOException;
import java.util.List;

public class RecipeListActivity extends AbstractMenuActivity {

    private final RecipeLoadStore storeData;
    private final FragmentInstaller installer;
    private final ActivityStarter starter;
    private final CurrentRecipe currentRecipe;

    private List<RecipeDao.RecipeInfo> names;
    private ListManagerFragment listManagerFragment;

    public RecipeListActivity() {
        storeData = RecipeLoadStore.getInstance();
        installer = new FragmentInstaller(this);
        starter = new ActivityStarter(this);
        currentRecipe = new CurrentRecipe(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeData.startGetRecipeList();
        setContentView(R.layout.activity_recipe_list);
        initListFragment();
        initRecipeList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        storeData.startGetRecipeList();
        initRecipeList();
    }

    public void addNewRecipe(View view) {
        currentRecipe.clear();
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivity(intent);
    }

    private void initListFragment() {
        listManagerFragment = new ListManagerFragment(this)
                .setEmptyListMessage(R.string.empty_recipe_list_message)
                .addItemClickListener(this::startShowRecipe);

        installer.installFragment(R.id.list_view_recipe_list, listManagerFragment);
    }


    private void initRecipeList() {
        names = storeData.commitGetRecipeList();
        listManagerFragment.updateList(names.stream().map(RecipeDao.RecipeInfo::getName));

    }


    private void startShowRecipe(int id) {
        RecipeDao.RecipeInfo info = names.get(id);
        try {
            currentRecipe.setCurrentRecipeName(info.getRecipeID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        starter.setActivity(ShowRecipeActivity.class)
                .start();

    }


}