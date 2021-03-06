package com.example.messychef;

import android.content.Intent;
import android.os.Bundle;

import com.example.messychef.recipe.load_store.RecipeLoadStore;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AbstractMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startApp();
            }
        }, 1000L);
        RecipeLoadStore.initInstance(getApplicationContext());
    }


    private void startApp() {
        Intent intent = new Intent(this, RecipeListActivity.class);
        startActivity(intent);
        finish();
    }

}