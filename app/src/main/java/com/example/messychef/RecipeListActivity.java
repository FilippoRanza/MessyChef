package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.messychef.list_manager.ListManagerFragment;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.storage_facility.FileInfo;
import com.example.messychef.storage_facility.StoreData;
import com.example.messychef.utils.FragmentInstaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeListActivity extends AppCompatActivity {

    private final StoreData storeData;
    private final FragmentInstaller installer;
    private FileInfo[] names;
    private ListManagerFragment listManagerFragment;

    public RecipeListActivity() {
        storeData = new StoreData(this);
        installer = new FragmentInstaller(this);
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
        Intent intent = new Intent(this, AddRecipeActivity.class);
        startActivity(intent);
    }

    private void initListFragment() {
        listManagerFragment = new ListManagerFragment(this)
                .setEmptyListMessage(R.string.empty_recipe_list_message);

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

}