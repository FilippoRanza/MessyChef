package com.example.messychef.utils;

import android.app.Activity;
import android.content.Intent;

public class ActivityStarter {
    final private Activity owner;

    public ActivityStarter(Activity owner) {
        this.owner = owner;
    }

    public void start(Class<?> cls) {
        Intent intent = new Intent(owner, cls);
        owner.startActivity(intent);
    }

    public void start(String name) {
        String fullName = "com.example.messychef." + name;
        Intent intent = new Intent(fullName);
        owner.startActivity(intent);
    }


}
