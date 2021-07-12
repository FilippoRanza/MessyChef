package com.example.messychef;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.messychef.delete_dialog.DeleteDialog;
import com.example.messychef.list_manager.ListManagerFragment;
import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeFactory;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class AbstractManageItemListActivity extends AppCompatActivity {

    final protected ActivityStarter starter;
    final protected ListManagerFragment list;
    final protected FragmentInstaller installer;

    public AbstractManageItemListActivity() {
        starter = new ActivityStarter(this);
        installer = new FragmentInstaller(this);
        list = new ListManagerFragment(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ingredient_list);
        initializeView();
        upgradeItemList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        upgradeItemList();
    }

    abstract public void startAddActivity(View v);



    abstract protected void initializeView();

    abstract protected void upgradeItemList();





}