package com.example.messychef;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import com.example.messychef.choose_complexity.ChooseComplexityFragment;
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


    private ChooseComplexityFragment chooseComplexity;
    private TextField field;

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


    private enum FilterType {
        Text,
        Complexity
    }

    private FilterType filterType;

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
        setClickListener(R.id.filter_by_name,
                actionBuilder(() -> mode = FilterMode.ByName, FilterType.Text))
                .toggle();

        setClickListener(R.id.filter_by_ingredient,
                actionBuilder(() -> mode = FilterMode.ByIngredient, FilterType.Text));

        setClickListener(R.id.filter_by_complexity,
                actionBuilder(() -> {
                }, FilterType.Complexity));
    }

    private RadioButton setClickListener(int id, View.OnClickListener listener) {
        RadioButton output = findViewById(id);
        output.setOnClickListener(listener);
        return output;
    }


    private View.OnClickListener actionBuilder(Runnable callback, FilterType type) {
        return v -> {
            if (filterType != type)
                enableType(type);
            callback.run();
        };
    }


    private void enableType(FilterType type) {
        switch (type) {
            case Text:
                initFilterFragment();
                break;
            case Complexity:
                initComplexityFragment();
                break;
        }
    }


    private void initFilterFragment() {
        field = new TextField(this, R.string.filter_placeholder)
                .addUpdateListener(this::startRecipeFilter)
                .addEditCompleteCallback(this::updateListOnSearchResult)
                .addFocusGetCallback(storeData::startCacheDatabase)
                .addFocusLostCallback(storeData::stopCacheDatabase)
                .allowEmpty();

        installFilterFragment(field);

        filterType = FilterType.Text;
    }

    private void initComplexityFragment() {
        chooseComplexity = new ChooseComplexityFragment()
                .setRadioSelectionListener(this::filterByComplexity);
        installFilterFragment(chooseComplexity);

        filterType = FilterType.Complexity;
        status = FilterStatus.Filtering;
    }

    private void filterByComplexity(int c) {
        storeData.startFilterRecipeByComplexity(c);
        updateListOnSearchResult();
    }

    private void installFilterFragment(Fragment fragment) {
        installer.removeFragment(R.id.filter_text_input);
        installer.installFragment(R.id.filter_text_input, fragment);
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
        if (status == FilterStatus.Filtering) {
            names = storeData.commitSearchRecipe();
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
        System.out.println(names);
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

    @Override
    protected void importDone() {
        super.importDone();
        storeData.startGetRecipeList();
        initRecipeList();
    }
}