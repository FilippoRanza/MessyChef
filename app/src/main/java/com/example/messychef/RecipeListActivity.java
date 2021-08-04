package com.example.messychef;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.messychef.list_manager.ListManagerFragment;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.load_store.RecipeLoadStore;
import com.example.messychef.storage_facility.CurrentRecipe;
import com.example.messychef.text_manager.TextField;
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

    private TextField field;

    private RadioButton filterRecipe;
    private RadioButton filterIngredient;

    private List<RecipeDao.RecipeInfo> fullList;

    private enum FilterStatus {
        Clear,
        Filtering
    }

    private FilterStatus status;

    private enum FilterMode {
        ByName,
        ByIngredient
    }

    private FilterMode mode;

    public RecipeListActivity() {
        storeData = RecipeLoadStore.getInstance();
        installer = new FragmentInstaller(this);
        starter = new ActivityStarter(this);
        currentRecipe = new CurrentRecipe(this);
        mode = FilterMode.ByName;
        status = FilterStatus.Clear;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeData.startGetRecipeList();
        setContentView(R.layout.activity_recipe_list);
        initFilterFragment();
        initListFragment();
        initRadioButtons();
        initRecipeList();
    }

    private void initRadioButtons() {
        System.out.println("START RADIO");
        filterRecipe = findViewById(R.id.filter_by_name);
        filterRecipe.setOnClickListener((v) -> mode = FilterMode.ByName);
        filterRecipe.toggle();

        filterIngredient = findViewById(R.id.filter_by_ingredient);
        filterIngredient.setOnClickListener((v) -> mode = FilterMode.ByIngredient);
    }

    private void initFilterFragment() {
        field = new TextField(this, R.string.filter_placeholder)
                .addUpdateListener(this::startRecipeFilter)
                .addEditCompleteCallback(this::updateListOnSearchResult)
                .addFocusGetCallback(storeData::startCacheDatabase)
                .addFocusLostCallback(storeData::stopCacheDatabase)
                .allowEmpty();

        installer.installFragment(R.id.filter_text_input, field);
    }

    private void startRecipeFilter(CharSequence cs) {
        if (cs.length() == 0) {
            status = FilterStatus.Clear;
        } else {
            startFilter(cs.toString());
            status = FilterStatus.Filtering;
        }

    }

    private void startFilter(String s) {
        if (mode == FilterMode.ByName)
            storeData.startSearchRecipe(s);
        else if (mode == FilterMode.ByIngredient)
            storeData.startSearchRecipeByIngredient(s);
    }


    private void updateListOnSearchResult() {
        if(status == FilterStatus.Filtering) {
            List<RecipeDao.RecipeInfo> list = storeData.commitSearchRecipe();
            System.out.println(list.size());
            names = list;
        } else {
            names = fullList;
        }
        applyList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        storeData.startGetRecipeList();
        field.clear();
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
        fullList = names;
        applyList();
    }

    private void applyList() {
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