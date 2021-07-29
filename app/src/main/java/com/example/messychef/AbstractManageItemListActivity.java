package com.example.messychef;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messychef.list_manager.ListManagerFragment;
import com.example.messychef.utils.ActivityStarter;
import com.example.messychef.utils.FragmentInstaller;

public abstract class AbstractManageItemListActivity extends AbstractMenuActivity {

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