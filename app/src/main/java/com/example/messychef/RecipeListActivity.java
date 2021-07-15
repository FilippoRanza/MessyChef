package com.example.messychef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.storage_facility.FileInfo;
import com.example.messychef.storage_facility.StoreData;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {

    private final StoreData storeData;


    public RecipeListActivity() {
        storeData = new StoreData(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
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


    private void initRecipeList() {
        FileInfo[] names = null;
        try {
            names = storeData.buildFileInfoList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<FileInfo> content = new ArrayAdapter<>(this, R.layout.list_element, names);

        AdapterView<ArrayAdapter<FileInfo>> adapter = findViewById(R.id.recipe_list);
        adapter.setAdapter(content);
    }

}