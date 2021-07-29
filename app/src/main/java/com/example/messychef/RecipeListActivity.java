package com.example.messychef;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.messychef.list_manager.ListManagerFragment;
import com.example.messychef.storage_facility.CurrentRecipe;
import com.example.messychef.storage_facility.FileInfo;
import com.example.messychef.storage_facility.StoreData;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;

import java.io.IOException;
import java.util.Arrays;

public class RecipeListActivity extends AbstractMenuActivity {

    private final StoreData storeData;
    private final FragmentInstaller installer;
    private final ActivityStarter starter;
    private final CurrentRecipe currentRecipe;

    private FileInfo[] names;
    private ListManagerFragment listManagerFragment;

    public RecipeListActivity() {
        storeData = new StoreData(this);
        installer = new FragmentInstaller(this);
        starter = new ActivityStarter(this);
        currentRecipe = new CurrentRecipe(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        initListFragment();
        initRecipeList();
    }


    @Override
    protected void onResume() {
        super.onResume();
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
        try {
            names = storeData.buildFileInfoList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listManagerFragment.updateList(Arrays.stream(names).map(FileInfo::toString));

    }

    private void startShowRecipe(int id) {
        FileInfo info = names[id];
        try {
            currentRecipe.setCurrentRecipeName(info.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        starter.setActivity(ShowRecipeActivity.class)
                .start();

    }


}